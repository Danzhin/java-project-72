package hexlet.code.model.entity;

import hexlet.code.utils.TimestampFormatter;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class Url {
    private int id;
    private String name;
    private String createdAt;

    public Url(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.createdAt = TimestampFormatter.toString(resultSet.getTimestamp("created_at"));
    }
}

