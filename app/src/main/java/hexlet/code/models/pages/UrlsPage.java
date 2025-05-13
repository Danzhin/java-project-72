package hexlet.code.models.pages;

import java.util.Map;

import hexlet.code.models.Url;

import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> urls;
}
