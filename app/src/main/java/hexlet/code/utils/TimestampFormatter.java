package hexlet.code.utils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String toString(Timestamp dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDateTime().format(FORMATTER);
    }

}
