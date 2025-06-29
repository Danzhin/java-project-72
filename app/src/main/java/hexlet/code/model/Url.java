package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Url {
    private final int id;
    private final String name;
    private final LocalDateTime createdAt;
}

