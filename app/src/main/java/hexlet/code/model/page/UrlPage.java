package hexlet.code.model.page;

import java.util.List;

import hexlet.code.model.entity.Url;
import hexlet.code.model.entity.UrlCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> checks;
}
