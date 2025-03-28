package hexlet.code.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hexlet.code.models.Url;

public class UrlRepository extends BaseRepository {

    public static void saveUrl(String name) throws SQLException {
        var sql = "insert into urls (name, created_at) values (?, ?)";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
            ) {
            prepareStatement.setString(1, name);
            prepareStatement.setTimestamp(2, getCurrentDateTime());
            prepareStatement.executeUpdate();
        }
    }

    public static Optional<Url> findUrl(int id) throws SQLException {
        var sql = "select * from urls where id = ?";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
        ) {
            prepareStatement.setInt(1, id);
            try (var resultSet = prepareStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(resultSetToUrl(resultSet)) : Optional.empty();
            }
        }
    }

    public static List<Url> getUrls() throws SQLException {
        var sql = "select * from urls";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql);
            var resultSet = prepareStatement.executeQuery()
        ) {
            var urls = new ArrayList<Url>();
            while (resultSet.next()) {
                urls.add(resultSetToUrl(resultSet));
            }
            return urls;
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
                return resultSet.next() && resultSet.getBoolean(1);
            }
        }
    }

    private static Url resultSetToUrl(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Url(id, name, createdAt);
    }

}

