package hexlet.code.controller;

import hexlet.code.exception.UrlValidationError;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import hexlet.code.model.page.BasePage;
import hexlet.code.model.page.UrlPage;
import hexlet.code.model.page.UrlsPage;
import hexlet.code.model.page.flash.Flash;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import org.apache.commons.validator.routines.UrlValidator;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    private static final UrlValidator VALIDATOR = new UrlValidator();

    public static void index(Context ctx) {
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-danger");
        var page = new BasePage();
        page.setFlash(flash);
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) {
        try {
            var name = prepareName(ctx.formParam("url"));
            UrlRepository.save(name);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect(Routes.URLS_PATH);
        } catch (UrlValidationError e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(Routes.ROOT_PATH);
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.redirect(Routes.ROOT_PATH);
        }
    }

    public static void readAll(Context ctx) throws SQLException {
        var urls = UrlRepository.readWithLatestChecks();
        var page = new UrlsPage(urls);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void readById(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.readById(id).orElseThrow(NotFoundResponse::new);
        var checks = UrlCheckRepository.readAll(id);
        var page = new UrlPage(url, checks);
        var flash = new Flash(ctx.consumeSessionAttribute("flash"), "alert-success");
        page.setFlash(flash);
        ctx.render("urls/show.jte", model("page", page));
    }

    private static String prepareName(String name) throws UrlValidationError {
        if (!VALIDATOR.isValid(name)) {
            throw new UrlValidationError("");
        }
        try {
            var uri = new URI(name);
            var url = uri.toURL();
            var protocol = url.getProtocol() + "://";
            var host = url.getHost();
            var port = url.getPort() == -1 ? "" : ":" + url.getPort();
            return protocol + host + port;
        } catch (URISyntaxException | MalformedURLException e) {
            throw new UrlValidationError("");
        }
    }

}


