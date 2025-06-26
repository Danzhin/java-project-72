package hexlet.code.page;

import java.util.List;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> checks;
}
