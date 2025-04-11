package hexlet.code.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {

    public static HikariDataSource dataSource;

    static Timestamp getCurrentDateTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    static String timestampToString(Timestamp dateTime) {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime == null ? null : dateTime.toLocalDateTime().format(formatter);
    }

}

