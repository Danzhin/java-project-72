package hexlet.code.utils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String toString(Timestamp dateTime) {
        return dateTime == null ? null : dateTime.toLocalDateTime().format(FORMATTER);
    }

}
