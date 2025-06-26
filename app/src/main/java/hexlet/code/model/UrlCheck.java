package hexlet.code.model;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static hexlet.code.utils.DateTimeUtils.timestampToLocalDateTime;

@Getter
public class UrlCheck {
    private final Integer id;
    private final int urlId;
    private final int statusCode;
    private final String title;
    private final String h1;
    private final String description;
    private final LocalDateTime createdAt;

    public UrlCheck(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.urlId = resultSet.getInt("url_id");
        this.statusCode = resultSet.getInt("status_code");
        this.h1 = resultSet.getString("h1");
        this.title = resultSet.getString("title");
        this.description = resultSet.getString("description");
        this.createdAt = timestampToLocalDateTime(resultSet.getTimestamp("created_at"));
    }
}
