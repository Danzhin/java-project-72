package hexlet.code.controllers;

import hexlet.code.utils.Routes;
import kong.unirest.Unirest;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repositories.UrlRepository;
import hexlet.code.models.Flash;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    private static final UrlValidator URL_VALIDATOR = new UrlValidator();

    public static void displaySearchForm(Context ctx) {
        var page = new BasePage();
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-danger");
        page.setFlash(flash);
        ctx.status(200);
        ctx.render("index.jte", model("page", page));
    }

    public static void displayUrls(Context ctx) throws SQLException {
        var urlsWithLatestChecks = UrlRepository.getUrlsWithLatestChecks();
        var page = new UrlsPage(urlsWithLatestChecks);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void displayUrl(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.getUrlById(id).orElseThrow(NotFoundResponse::new);
        var checks = UrlRepository.getUrlChecks(id);
        var page = new UrlPage(url, checks);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void addUrl(Context ctx) throws SQLException {
        try {
            var name = ctx.formParam("url");
            if (!URL_VALIDATOR.isValid(name)) {
                throw new IllegalArgumentException();
            }
            var formattedName = formatName(name);
            if (UrlRepository.containsName(formattedName)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(Routes.rootPath());
                return;
            }
            UrlRepository.saveUrl(name);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(Routes.urlsPath());
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(Routes.rootPath());
        }
    }

    public static void addUrlCheck(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.getUrlById(urlId).orElseThrow(NotFoundResponse::new);
        var response = Unirest.get(url.getName()).asString();

        var statusCode = response.getStatus();
        var document = Jsoup.parse(response.getBody());
        var h1Element = document.selectFirst("h1");
        var h1 = (h1Element != null) ? h1Element.text() : null;
        var title = document.title();
        var descriptionElement = document.selectFirst("meta[name=description]");
        var description = (descriptionElement != null) ? descriptionElement.attr("content") : null;

        UrlRepository.saveUrlCheck(urlId, statusCode, h1, title, description);
        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.redirect(Routes.urlPath(urlId));
    }

    public static String formatName(String name) throws URISyntaxException, MalformedURLException {
        var uri = new URI(name);
        var url = uri.toURL();
        var protocol = url.getProtocol() + "://";
        var host = url.getHost();
        var port = url.getPort() == -1 ? "" : ":" + url.getPort();
        return protocol + host + port;
    }

}

