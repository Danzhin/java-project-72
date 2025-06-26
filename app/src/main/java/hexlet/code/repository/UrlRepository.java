package hexlet.code.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

public class UrlRepository extends BaseRepository {

    public static void save(String name) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (
                var connection = getConnection();
                var preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        }
    }

    public static Optional<Url> readById(int id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (
                var connection = getConnection();
                var preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(new Url(resultSet)) : Optional.empty();
            }
        }
    }

    public static Map<Url, UrlCheck> readWithLatestChecks() throws SQLException {
        var sql = """
            SELECT
            urls.id, urls.name,
            url_checks.*
            FROM urls
            LEFT JOIN (
                SELECT
                    url_checks.id, url_checks.status_code, url_checks.h1,
                    url_checks.title, url_checks.description, url_checks.created_at,
                    url_checks.url_id,
                    ROW_NUMBER() OVER (PARTITION BY url_checks.url_id ORDER BY url_checks.created_at DESC) AS rn
                FROM url_checks
            ) AS url_checks
            ON urls.id = url_checks.url_id AND url_checks.rn = 1;
            """;
        try (
                var connection = getConnection();
                var preparedStatement = connection.prepareStatement(sql);
                var resultSet = preparedStatement.executeQuery()
        ) {
            var result = new HashMap<Url, UrlCheck>();
            while (resultSet.next()) {
                var url = new Url(resultSet);
                var urlCheck = new UrlCheck(resultSet);
                result.put(url, urlCheck);
            }
            return result;
        }
    }

}

