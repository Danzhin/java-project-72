package hexlet.code.models;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class UrlCheck {
    private Integer id;
    private int urlId;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private String createdAt;
}
