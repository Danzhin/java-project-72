package hexlet.code.models.pages;

import java.util.List;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> checks;
}
