package hexlet.code.model;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static hexlet.code.utils.DateTimeUtils.timestampToLocalDateTime;

@Getter
public class Url {
    private final int id;
    private final String name;
    private final LocalDateTime createdAt;

    public Url(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.createdAt = timestampToLocalDateTime(resultSet.getTimestamp("created_at"));
    }
}

