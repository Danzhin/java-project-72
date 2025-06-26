package hexlet.code.controller;

import hexlet.code.utils.FlashAttributes;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.SQLException;
import java.util.Optional;

public class UrlChecksController {

    public static void create(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.readById(urlId).orElseThrow(NotFoundResponse::new);

        HttpResponse<String> response;
        try {
            response = Unirest.get(url.getName()).asString();
        } catch (UnirestException e) {
            ctx.sessionAttribute(FlashAttributes.FLASH, FlashAttributes.CHECK_URL_ERROR_MASSAGE);
            ctx.redirect(Routes.urlPath(urlId));
            return;
        }

        var document = Jsoup.parse(response.getBody());
        var statusCode = response.getStatus();
        var h1 = getH1(document);
        var title = document.title();
        var description = getDescription(document);

        UrlCheckRepository.save(urlId, statusCode, h1, title, description);
        ctx.sessionAttribute(FlashAttributes.FLASH, FlashAttributes.CHECK_URL_SUCCESS_MASSAGE);
        ctx.redirect(Routes.urlPath(urlId));
    }

    private static String getH1(Document document) {
        return Optional.ofNullable(document.selectFirst("h1"))
                .map(Element::text)
                .orElse(null);
    }

    private static String getDescription(Document document) {
        return Optional.ofNullable(document.selectFirst("meta[name=description]"))
                .map(el -> el.attr("content"))
                .orElse(null);
    }

}
