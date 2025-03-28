package hexlet.code.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hexlet.code.models.UrlCheck;

public class UrlCheckRepository extends BaseRepository {

    public static void saveUrlCheck(int urlId, int statusCode,
                                    String h1, String title,
                                    String description) throws SQLException {
        var sql = "insert into url_checks (url_id, status_code, h1, title, description, created_at)"
                + " values (?, ?, ?, ?, ?, ?)";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
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

    public static List<UrlCheck> getUrlChecks(int urlId) throws SQLException {
        var sql = "select * from url_checks where url_id = ?";
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
            ) {
            prepareStatement.setInt(1, urlId);
            try (var resultSet = prepareStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    result.add(resultSetToUrlCheck(resultSet));
                }
                return result;
            }
        }
    }

    private static UrlCheck resultSetToUrlCheck(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var urlId = resultSet.getInt("url_id");
        var statusCode = resultSet.getInt("status_code");
        var h1 = resultSet.getString("h1");
        var title = resultSet.getString("title");
        var description = resultSet.getString("description");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new UrlCheck(id, urlId, statusCode, h1, title, description, createdAt);
    }

}
