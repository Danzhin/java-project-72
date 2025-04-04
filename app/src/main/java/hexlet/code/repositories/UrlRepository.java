package hexlet.code.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.utils.SqlRequests;
import hexlet.code.utils.TimestampFormatter;

public class UrlRepository extends BaseRepository {

    private static Url buildUrl(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var createdAt = TimestampFormatter.toString(resultSet.getTimestamp("created_at"));
        return new Url(id, name, createdAt);
    }

    public static void saveUrl(String name) throws SQLException {
        var sql = SqlRequests.SAVE_URL;
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
            ) {
            prepareStatement.setString(1, name);
            prepareStatement.setTimestamp(2, getCurrentDateTime());
            prepareStatement.executeUpdate();
        }
    }

    public static Optional<Url> getUrlById(int id) throws SQLException {
        var sql = SqlRequests.GET_URL_BY_ID;
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
        var sql = SqlRequests.CONTAINS_NAME;
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

    private static UrlCheck buildUrlCheck(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var urlId = resultSet.getInt("url_id");
        var statusCode = resultSet.getInt("status_code");
        var h1 = resultSet.getString("h1");
        var title = resultSet.getString("title");
        var description = resultSet.getString("description");
        var createdAt = TimestampFormatter.toString(resultSet.getTimestamp("created_at"));
        return new UrlCheck(id, urlId, statusCode, h1, title, description, createdAt);
    }

    public static void saveUrlCheck(int urlId, int statusCode, String h1,
                                    String title, String description) throws SQLException {
        var sql = SqlRequests.SAVE_URL_CHECK;
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
        var sql = SqlRequests.GET_URL_CHECKS;
        try (
            var connection = dataSource.getConnection();
            var prepareStatement = connection.prepareStatement(sql)
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

    public static Map<Url, UrlCheck> getUrlsWithLatestChecks() throws SQLException {
        var sql = SqlRequests.GET_URLS_WITH_LATEST_CHECKS;
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

    public static void clear() throws SQLException {
        var sql = SqlRequests.CLEAR;
        try (
            var connection = dataSource.getConnection();
            var statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        }
    }

}

