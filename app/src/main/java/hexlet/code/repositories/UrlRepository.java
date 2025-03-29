package hexlet.code.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.utils.TimestampFormatter;

import static hexlet.code.repositories.UrlCheckRepository.buildUrlCheck;

public class UrlRepository extends BaseRepository {

    public static void saveUrl(String name) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
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
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
        ) {
            prepareStatement.setInt(1, id);
            try (var resultSet = prepareStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(buildUrl(resultSet)) : Optional.empty();
            }
        }
    }

    public static boolean containsName(String name) throws SQLException {
        var sql = "SELECT EXISTS (SELECT 1 FROM urls WHERE name = ?)";
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

    public static Map<Url, UrlCheck> getUrls() throws SQLException {
        var sql = """
            SELECT
                urls.id, urls.name,
                url_checks.*
            FROM urls
            LEFT JOIN (
                SELECT
                    url_checks.id, url_checks.status_code, url_checks.h1,
                    url_checks.title, url_checks.description, url_checks.created_at,
                    url_checks.url_id,  -- добавляем столбец url_id
                    ROW_NUMBER() OVER (PARTITION BY url_checks.url_id ORDER BY url_checks.created_at DESC) AS rn
                FROM url_checks
            ) AS url_checks
            ON urls.id = url_checks.url_id AND url_checks.rn = 1;
            """;

        try (
            var connection = dataSource.getConnection();
            var preparedStatement = connection.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery()
        ) {
            var result = new HashMap<Url, UrlCheck>();
            while (resultSet.next()) {
                var url = buildUrl(resultSet);
                var urlCheck = buildUrlCheck(resultSet);
                result.put(url, urlCheck);
            }
            return result;
        }
    }

    private static Url buildUrl(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var createdAt = TimestampFormatter.toString(resultSet.getTimestamp("created_at"));
        return new Url(id, name, createdAt);
    }

}

