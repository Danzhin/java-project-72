package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.models.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Url url;
}
