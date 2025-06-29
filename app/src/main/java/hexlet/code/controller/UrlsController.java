package hexlet.code.controller;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

import hexlet.code.page.BasePage;
import hexlet.code.page.UrlPage;
import hexlet.code.page.UrlsPage;
import hexlet.code.page.flash.Flash;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import org.apache.commons.validator.routines.UrlValidator;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    private static final UrlValidator VALIDATOR = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);

    public static void index(Context ctx) {
        var flash = new Flash(ctx.consumeSessionAttribute(ControllerUtils.FLASH), ControllerUtils.ALERT_TYPE_DANGER);
        var page = new BasePage();
        page.setFlash(flash);
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var name = ctx.formParam("url");
        URI uri;
        try {
            uri = new URI(Objects.requireNonNull(name));
            if (!VALIDATOR.isValid(name)) {
                throw new URISyntaxException(name, "");
            }
        } catch (URISyntaxException e) {
            ctx.sessionAttribute(ControllerUtils.FLASH, ControllerUtils.INCORRECT_URL_MASSAGE);
            ctx.redirect(Routes.ROOT_PATH);
            return;
        }
        var scheme = uri.getScheme();
        var host = uri.getHost();
        var port = uri.getPort() == -1 ? "" : ":" + uri.getPort();
        var preparedName = String.format("%s://%s%s", scheme, host, port).toLowerCase();
        try {
            UrlRepository.save(String.format(preparedName));
        } catch (SQLIntegrityConstraintViolationException e) {
            ctx.sessionAttribute(ControllerUtils.FLASH, ControllerUtils.URL_ALREADY_EXISTS_MASSAGE);
            ctx.redirect(Routes.ROOT_PATH);
            return;
        }
        ctx.sessionAttribute(ControllerUtils.FLASH, ControllerUtils.CREATE_URL_SUCCESS_MASSAGE);
        ctx.redirect(Routes.URLS_PATH);
    }

    public static void readAll(Context ctx) throws SQLException {
        var urls = UrlRepository.readWithLatestChecks();
        var page = new UrlsPage(urls);
        var flash = new Flash(ctx.consumeSessionAttribute(ControllerUtils.FLASH), ControllerUtils.ALERT_TYPE_SUCCESS);
        page.setFlash(flash);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void readById(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.readById(id).orElseThrow(NotFoundResponse::new);
        var checks = UrlCheckRepository.readAll(id);
        var page = new UrlPage(url, checks);
        var flash = new Flash(ctx.consumeSessionAttribute(ControllerUtils.FLASH), ControllerUtils.ALERT_TYPE_SUCCESS);
        page.setFlash(flash);
        ctx.render("urls/show.jte", model("page", page));
    }

}


