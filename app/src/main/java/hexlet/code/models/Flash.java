package hexlet.code.models;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Flash {
    private String massage;
    private String alertType;
}
