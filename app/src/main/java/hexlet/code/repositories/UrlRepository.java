package hexlet.code.repositories;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hexlet.code.models.Url;

public class UrlRepository extends BaseRepository {

    public static void save(String name) throws SQLException {
        var sql = "insert into urls (name, createdAt) values (?, ?)";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
            ) {
            var createdAt = Timestamp.valueOf(LocalDateTime.now());
            prepareStatement.setString(1, name);
            prepareStatement.setTimestamp(2, createdAt);
            prepareStatement.executeUpdate();
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "select * from urls where id = ?";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
            ) {
            prepareStatement.setLong(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                var url = new Url(id, name, createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static List<Url> getUrls() throws SQLException {
        var sql = "select * from urls";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
            ) {
            var resultSet = prepareStatement.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                var url = new Url(id, name, createdAt);
                result.add(url);
            }
            return result;
        }
    }

    public static boolean containsName(String name) throws SQLException {
        var sql = "select exists (select 1 from urls where name = ?)";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
        ) {
            prepareStatement.setString(1, name);
            try (var resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        }
        return false;
    }

}

