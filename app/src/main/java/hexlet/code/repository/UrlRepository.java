package hexlet.code.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hexlet.code.model.Url;

public class UrlRepository extends BaseRepository {

    public static void save(String name) throws SQLException {
        var sql = "insert into urls (name, createdAt) values (?, ?)";
        try (var prepareStatement = dataSource.getConnection()
            .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var createdAt = Timestamp.valueOf(LocalDateTime.now());
            prepareStatement.setString(1, name);
            prepareStatement.setTimestamp(2, createdAt);
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException();
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "select * from urls where id = ?";
        try (var prepareStatement = dataSource.getConnection().prepareStatement(sql)) {
            prepareStatement.setLong(1, id);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt");
                var url = new Url(id, name, createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "select * from urls";
        try (var prepareStatement = dataSource.getConnection().prepareStatement(sql)) {
            var resultSet = prepareStatement.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt");
                var url = new Url(id, name, createdAt);
                result.add(url);
            }
            return result;
        }
    }

}

