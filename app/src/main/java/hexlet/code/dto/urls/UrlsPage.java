package hexlet.code.dto.urls;

import java.util.Map;

import hexlet.code.dto.BasePage;
import hexlet.code.models.Url;

import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private Map<Url, UrlCheck> urls;
}
