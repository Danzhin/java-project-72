package hexlet.code.model.entity;

import hexlet.code.utils.TimestampFormatter;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class UrlCheck {
    private Integer id;
    private int urlId;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private String createdAt;

    public UrlCheck(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.urlId = resultSet.getInt("url_id");
        this.statusCode = resultSet.getInt("status_code");
        this.h1 = resultSet.getString("h1");
        this.title = resultSet.getString("title");
        this.description = resultSet.getString("description");
        this.createdAt = TimestampFormatter.toString(resultSet.getTimestamp("created_at"));
    }
}
