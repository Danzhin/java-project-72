package hexlet.code.controller;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import kong.unirest.Unirest;

import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.sql.SQLException;
import java.util.Optional;

public class UrlChecksController {

    public static void create(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.readById(urlId).orElseThrow(NotFoundResponse::new);
        try {
            var response = Unirest.get(url.getName()).asString();
            var statusCode = response.getStatus();
            var document = Jsoup.parse(response.getBody());
            var h1 = Optional.ofNullable(document.selectFirst("h1")).map(Element::text).orElse(null);
            var title = document.title();
            var description = Optional.ofNullable(document.selectFirst("meta[name=description]"))
                    .map(el -> el.attr("content")).orElse(null);
            UrlCheckRepository.save(urlId, statusCode, h1, title, description);
            ctx.sessionAttribute(ControllerUtils.FLASH, ControllerUtils.CHECK_URL_SUCCESS_MASSAGE);
            ctx.redirect(Routes.urlPath(urlId));
        } catch (UnirestException | NullPointerException e) {
            ctx.sessionAttribute(ControllerUtils.FLASH, ControllerUtils.CHECK_URL_ERROR_MASSAGE);
            ctx.redirect(Routes.urlPath(urlId));
        }
    }

}
