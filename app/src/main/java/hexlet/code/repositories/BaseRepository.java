package hexlet.code.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {

    public static HikariDataSource dataSource;

    public static Timestamp getCurrentDateTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

}

