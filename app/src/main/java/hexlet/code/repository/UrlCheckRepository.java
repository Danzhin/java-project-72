package hexlet.code.repository;

import hexlet.code.model.entity.UrlCheck;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {

    private static final String SQL_SAVE = """
            INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String SQL_READ_ALL = "SELECT * FROM url_checks WHERE url_id = ?";

    public static void save(int urlId, int statusCode, String h1,
                            String title, String description) throws SQLException {
        try (
                var connection = dataSource.getConnection();
                var preparedStatement = connection.prepareStatement(SQL_SAVE)
        ) {
            preparedStatement.setInt(1, urlId);
            preparedStatement.setInt(2, statusCode);
            preparedStatement.setString(3, h1);
            preparedStatement.setString(4, title);
            preparedStatement.setString(5, description);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        }
    }

    public static List<UrlCheck> readAll(int urlId) throws SQLException {
        try (
                var connection = dataSource.getConnection();
                var preparedStatement = connection.prepareStatement(SQL_READ_ALL)
        ) {
            preparedStatement.setInt(1, urlId);
            try (var resultSet = preparedStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    result.add(new UrlCheck(resultSet));
                }
                return result;
            }
        }
    }

}
