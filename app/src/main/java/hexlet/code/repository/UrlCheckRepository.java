package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hexlet.code.utils.DateTimeUtils.timestampToLocalDateTime;

public class UrlCheckRepository extends BaseRepository {

    public static int save(int urlId, int statusCode, String title, String h1,
                           String description) throws SQLException {
        var sql = """
            INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (
                var connection = getConnection();
                var statement = prepareStatement(connection, sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, urlId);
            statement.setInt(2, statusCode);
            statement.setString(3, title);
            statement.setString(4, h1);
            statement.setString(5, description);
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            try (var generatedKeys = statement.getGeneratedKeys()) {
                return generatedKeys.next() ? generatedKeys.getInt(1) : 0;
            }
        }
    }

    public static List<UrlCheck> readAll(int urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        try (
                var connection = getConnection();
                var statement = prepareStatement(connection, sql)
        ) {
            statement.setInt(1, urlId);
            try (var resultSet = statement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    var id = resultSet.getInt("id");
                    var statusCode = resultSet.getInt("status_code");
                    var h1 = resultSet.getString("h1");
                    var title = resultSet.getString("title");
                    var description = resultSet.getString("description");
                    var createdAt = timestampToLocalDateTime(resultSet.getTimestamp("created_at"));
                    var urlCheck = new UrlCheck(id, urlId, statusCode, h1, title, description, createdAt);
                    result.add(urlCheck);
                }
                return result;
            }
        }
    }

}
