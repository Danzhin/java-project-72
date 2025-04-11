package hexlet.code.pages.urls;

import java.util.Map;

import hexlet.code.pages.BasePage;
import hexlet.code.models.Url;

import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> urls;
}
