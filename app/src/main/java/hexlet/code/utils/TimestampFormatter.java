package hexlet.code.utils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TimestampFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String toString(Timestamp createdAt) {
        return Optional.ofNullable(createdAt)
                .map(Timestamp::toLocalDateTime)
                .map(FORMATTER::format)
                .orElse(null);
    }

}
