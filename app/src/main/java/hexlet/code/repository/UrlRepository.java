package hexlet.code.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import hexlet.code.model.entity.Url;
import hexlet.code.model.entity.UrlCheck;

public class UrlRepository extends BaseRepository {

    public static void save(String name) throws SQLException {
        var request = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (
                var connection = dataSource.getConnection();
                var prepareStatement = connection.prepareStatement(request)
        ) {
            prepareStatement.setString(1, name);
            prepareStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            prepareStatement.executeUpdate();
        }
    }

    public static Optional<Url> readById(int id) throws SQLException {
        var request = "SELECT * FROM urls WHERE id = ?";
        try (
                var connection = dataSource.getConnection();
                var prepareStatement = connection.prepareStatement(request)
        ) {
            prepareStatement.setInt(1, id);
            try (var resultSet = prepareStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(new Url(resultSet)) : Optional.empty();
            }
        }
    }

    public static Map<Url, UrlCheck> readWithLatestChecks() throws SQLException {
        var request = """
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
                var connection = dataSource.getConnection();
                var preparedStatement = connection.prepareStatement(request);
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

