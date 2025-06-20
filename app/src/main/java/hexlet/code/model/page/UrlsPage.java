package hexlet.code.model.page;

import java.util.Map;

import hexlet.code.model.entity.Url;
import hexlet.code.model.entity.UrlCheck;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> urls;
}
