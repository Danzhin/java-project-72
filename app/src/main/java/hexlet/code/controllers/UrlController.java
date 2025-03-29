package hexlet.code.controllers;

import java.sql.SQLException;

import hexlet.code.models.Flash;
import kong.unirest.Unirest;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repositories.UrlCheckRepository;
import hexlet.code.repositories.UrlRepository;
import hexlet.code.utils.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    private static final UrlValidator URL_VALIDATOR = new UrlValidator();

    public static void index(Context ctx) {
        var page = new BasePage();
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-danger");
        page.setFlash(flash);
        ctx.render("index.jte", model("page", page));
    }

    public static void showUrls(Context ctx) throws SQLException {
        var urls = UrlRepository.getUrls();
        var page = new UrlsPage(urls);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.findUrl(id).orElseThrow(NotFoundResponse::new);
        var checks = UrlCheckRepository.getUrlChecks(id);
        var page = new UrlPage(url, checks);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void createUrl(Context ctx) throws SQLException {
        try {
            var name = ctx.formParam("url");
            if (!URL_VALIDATOR.isValid(name)) {
                throw new IllegalArgumentException();
            }
            var formattedName = formatName(name);
            if (UrlRepository.containsName(formattedName)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.rootPath());
                return;
            }
            UrlRepository.saveUrl(name);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static String formatName(String name) throws URISyntaxException, MalformedURLException {
        var uri = new URI(name);
        var url = uri.toURL();
        var protocol = url.getProtocol() + "://";
        var host = url.getHost();
        var port = url.getPort() == -1 ? "" : ":" + url.getPort();
        return protocol + host + port;
    }

    public static void createUrlCheck(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.findUrl(urlId).orElseThrow(NotFoundResponse::new);
        var response = Unirest.get(url.getName()).asString();

        var statusCode = response.getStatus();
        var document = Jsoup.parse(response.getBody());
        var h1Element = document.selectFirst("h1");
        var h1 = (h1Element != null) ? h1Element.text() : null;
        var title = document.title();
        var descriptionElement = document.selectFirst("meta[name=description]");
        var description = (descriptionElement != null) ? descriptionElement.attr("content") : null;

        UrlCheckRepository.saveUrlCheck(urlId, statusCode, h1, title, description);
        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }

}

