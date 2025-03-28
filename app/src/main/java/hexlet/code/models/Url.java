package hexlet.code.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public final class Url {

    private int id;
    private String name;
    private LocalDateTime createdAt;

}

