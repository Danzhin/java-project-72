package hexlet.code.models;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public final class Url {
    private int id;
    private String name;
    private String createdAt;
}

