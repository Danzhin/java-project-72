package hexlet.code.repository;

import hexlet.code.model.entity.UrlCheck;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {

    public static void save(int urlId, int statusCode, String h1,
                            String title, String description) throws SQLException {
        var request = """
            INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (
                var connection = dataSource.getConnection();
                var prepareStatement = connection.prepareStatement(request)
        ) {
            prepareStatement.setInt(1, urlId);
            prepareStatement.setInt(2, statusCode);
            prepareStatement.setString(3, h1);
            prepareStatement.setString(4, title);
            prepareStatement.setString(5, description);
            prepareStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            prepareStatement.executeUpdate();
        }
    }

    public static List<UrlCheck> readAllWithId(int urlId) throws SQLException {
        var request = "SELECT * FROM url_checks WHERE url_id = ?";
        try (
                var connection = dataSource.getConnection();
                var prepareStatement = connection.prepareStatement(request)
        ) {
            prepareStatement.setInt(1, urlId);
            try (var resultSet = prepareStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    result.add(new UrlCheck(resultSet));
                }
                return result;
            }
        }
    }

}
