package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UrlCheck {
    private final Integer id;
    private final int urlId;
    private final int statusCode;
    private final String title;
    private final String h1;
    private final String description;
    private final LocalDateTime createdAt;
}
