package hexlet.code.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;

public class UrlRepository extends BaseRepository {

    public static final String SAVE_URL = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

    public static final String READ_URL_BY_ID = "SELECT * FROM urls WHERE id = ?";

    public static final String READ_URL_BY_NAME = "SELECT * FROM urls WHERE name = ?";

    public static final String READ_URLS_WITH_LATEST_CHECKS = """
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

    public static final String SAVE_URL_CHECK = """
        INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    public static final String READ_URL_CHECKS = "SELECT * FROM url_checks WHERE url_id = ?";

    static Timestamp getCurrentDateTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    static String createdAtToString(Timestamp createdAt) {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return Optional.ofNullable(createdAt)
                .map(Timestamp::toLocalDateTime)
                .map(formatter::format)
                .orElse(null);
    }

    private static Url buildUrl(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at");
        return new Url(id, name, createdAtToString(createdAt));
    }

    public static void saveUrl(String name) throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(SAVE_URL)
            ) {
            prepareStatement.setString(1, name);
            prepareStatement.setTimestamp(2, getCurrentDateTime());
            prepareStatement.executeUpdate();
        }
    }

    public static Optional<Url> readUrlById(int id) throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(READ_URL_BY_ID)
        ) {
            prepareStatement.setInt(1, id);
            try (var resultSet = prepareStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(buildUrl(resultSet)) : Optional.empty();
            }
        }
    }

    public static Optional<Url> readUrlByName(String name) throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(READ_URL_BY_NAME)
        ) {
            prepareStatement.setString(1, name);
            try (var resultSet = prepareStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(buildUrl(resultSet)) : Optional.empty();
            }
        }
    }

    private static UrlCheck buildUrlCheck(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var urlId = resultSet.getInt("url_id");
        var statusCode = resultSet.getInt("status_code");
        var h1 = resultSet.getString("h1");
        var title = resultSet.getString("title");
        var description = resultSet.getString("description");
        var createdAt = resultSet.getTimestamp("created_at");
        return new UrlCheck(id, urlId, statusCode, h1, title, description, createdAtToString(createdAt));
    }

    public static void saveUrlCheck(int urlId, int statusCode, String h1,
                                    String title, String description) throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(SAVE_URL_CHECK)
        ) {
            prepareStatement.setInt(1, urlId);
            prepareStatement.setInt(2, statusCode);
            prepareStatement.setString(3, h1);
            prepareStatement.setString(4, title);
            prepareStatement.setString(5, description);
            prepareStatement.setTimestamp(6, getCurrentDateTime());
            prepareStatement.executeUpdate();
        }
    }

    public static List<UrlCheck> readUrlChecks(int urlId) throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(READ_URL_CHECKS)
        ) {
            prepareStatement.setInt(1, urlId);
            try (var resultSet = prepareStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    result.add(buildUrlCheck(resultSet));
                }
                return result;
            }
        }
    }

    public static Map<Url, UrlCheck> readUrlsWithLatestChecks() throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var preparedStatement = connection.prepareStatement(READ_URLS_WITH_LATEST_CHECKS);
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

}

