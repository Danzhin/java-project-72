package hexlet.code;

import java.sql.SQLException;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;
import hexlet.code.controller.UrlController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) throws SQLException {
        var app = getApp();
        var port = getPort();
        app.start(port);
    }

    public static Javalin getApp() throws SQLException {
        var jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        UrlRepository.launch(jdbcUrl);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(Routes.ROOT_PATH, UrlController::readSearchForm);
        app.get(Routes.URLS_PATH, UrlController::readUrls);
        app.get(Routes.URL_PATH, UrlController::readUrl);
        app.post(Routes.URLS_PATH, UrlController::createUrl);
        app.post(Routes.URLS_CHECKS_PATH, UrlController::createUrlCheck);

        return app;
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

}
