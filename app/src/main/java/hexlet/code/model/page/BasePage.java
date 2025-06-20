package hexlet.code.model.page;

import hexlet.code.model.page.flash.Flash;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasePage {
    private Flash flash;
}
