package hexlet.code.controllers;

import java.sql.SQLException;

import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repositories.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getUrls();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse());
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var name = ctx.formParam("url");
            UrlValidator urlValidator = new UrlValidator();
            if (!urlValidator.isValid(name)) {
                throw new IllegalArgumentException();
            }
            var uri = new URI(name);
            var url = uri.toURL();
            var protocol = url.getProtocol() + "://";
            var host = url.getHost();
            var port = url.getPort() == -1 ? "" : ":" + url.getPort();
            name = protocol + host + port;
            if (UrlRepository.containsName(name)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect(NamedRoutes.rootPath());
                return;
            }
            UrlRepository.save(name);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Ошибка обработки URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

}

