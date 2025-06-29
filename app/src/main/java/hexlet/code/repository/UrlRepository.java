package hexlet.code.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import static hexlet.code.utils.DateTimeUtils.timestampToLocalDateTime;

public class UrlRepository extends BaseRepository {

    public static int save(String name) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (
                var connection = getConnection();
                var statement = prepareStatement(connection, sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, name);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            try (var generatedKeys = statement.getGeneratedKeys()) {
                return generatedKeys.next() ? generatedKeys.getInt(1) : 0;
            }
        }
    }

    public static Optional<Url> readById(int id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (
                var connection = getConnection();
                var statement = prepareStatement(connection, sql)
        ) {
            statement.setInt(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    var name = resultSet.getString("name");
                    var createdAt = timestampToLocalDateTime(resultSet.getTimestamp("created_at"));
                    var url = new Url(id, name, createdAt);
                    return Optional.of(url);
                }
                return Optional.empty();
            }
        }
    }

    public static Map<Url, UrlCheck> readWithLatestChecks() throws SQLException {
        var sql = """
            SELECT
                urls.id AS url_id,
                urls.name,
                urls.created_at AS url_created_at,
                url_checks.id AS url_check_id,
                url_checks.url_id,
                url_checks.status_code,
                url_checks.h1,
                url_checks.title,
                url_checks.description,
                url_checks.created_at AS url_check_created_at
            FROM urls
            LEFT JOIN (
                SELECT
                    url_checks.id,
                    url_checks.url_id,
                    url_checks.status_code,
                    url_checks.h1,
                    url_checks.title,
                    url_checks.description,
                    url_checks.created_at,
                    ROW_NUMBER() OVER (PARTITION BY url_checks.url_id ORDER BY url_checks.created_at DESC) AS rn
                FROM url_checks
            ) AS url_checks
            ON urls.id = url_checks.url_id AND url_checks.rn = 1;
            """;
        try (
                var connection = getConnection();
                var statement = prepareStatement(connection, sql);
                var resultSet = statement.executeQuery()
        ) {
            var result = new HashMap<Url, UrlCheck>();
            while (resultSet.next()) {
                var urlId = resultSet.getInt("url_id");
                var urlName = resultSet.getString("name");
                var urlCreatedAt = timestampToLocalDateTime(resultSet.getTimestamp("url_created_at"));
                var url = new Url(urlId, urlName, urlCreatedAt);

                var urlCheckId = resultSet.getInt("url_check_id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var urlCheckCreatedAt = timestampToLocalDateTime(resultSet.getTimestamp("url_check_created_at"));
                var urlCheck = new UrlCheck(urlCheckId, urlId, statusCode, title, h1, description, urlCheckCreatedAt);

                result.put(url, urlCheck);
            }
            return result;
        }
    }

}

