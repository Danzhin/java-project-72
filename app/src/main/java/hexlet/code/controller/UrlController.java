package hexlet.code.controller;

import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;
import kong.unirest.Unirest;

import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

import hexlet.code.models.pages.BasePage;
import hexlet.code.models.pages.UrlPage;
import hexlet.code.models.pages.UrlsPage;
import hexlet.code.models.Flash;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jsoup.nodes.Element;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void readSearchForm(Context ctx) {
        var page = new BasePage();
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-danger");
        page.setFlash(flash);
        ctx.render("index.jte", model("page", page));
    }

    public static void readUrls(Context ctx) throws SQLException {
        var urlsWithLatestChecks = UrlRepository.readUrlsWithLatestChecks();
        var page = new UrlsPage(urlsWithLatestChecks);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void readUrl(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.readUrlById(id).orElseThrow(NotFoundResponse::new);
        var checks = UrlRepository.readUrlChecks(id);
        var page = new UrlPage(url, checks);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void createUrl(Context ctx) throws SQLException {
        try {
            var name = UrlFormatter.normalize(ctx.formParam("url"));
            var url = UrlRepository.readUrlByName(name).orElse(null);
            if (url != null) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(Routes.ROOT_PATH);
                return;
            }
            UrlRepository.saveUrl(name);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(Routes.URLS_PATH);
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(Routes.ROOT_PATH);
        }
    }

    public static void createUrlCheck(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.readUrlById(urlId).orElseThrow(NotFoundResponse::new);
        var response = Unirest.get(url.getName()).asString();
        var document = Jsoup.parse(response.getBody());

        var statusCode = response.getStatus();
        var h1 = Optional.ofNullable(document.selectFirst("h1"))
                .map(Element::text)
                .orElse(null);
        var title = document.title();
        var description = Optional.ofNullable(document.selectFirst("meta[name=description]"))
                .map(el -> el.attr("content"))
                .orElse(null);

        UrlRepository.saveUrlCheck(urlId, statusCode, h1, title, description);
        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.redirect(Routes.urlPath(urlId));
    }

}


