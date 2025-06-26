package hexlet.code.page;

import java.util.Map;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> urls;
}
