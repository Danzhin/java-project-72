package hexlet.code.utils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Optional;

public class DateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String localDateTimeToString(LocalDateTime dateTime) {
        return Optional.ofNullable(dateTime)
                .map(FORMATTER::format)
                .orElse(null);
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp dateTime) {
        return Optional.ofNullable(dateTime)
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }

}
