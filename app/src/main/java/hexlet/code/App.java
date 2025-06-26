package hexlet.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import hexlet.code.controller.UrlChecksController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.utils.Routes;
import hexlet.code.controller.UrlsController;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) throws SQLException, IOException {
        var app = getApp();
        var port = getPort();
        app.start(port);
    }

    public static Javalin getApp() throws SQLException, IOException {
        var jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        BaseRepository.dataSource = new HikariDataSource(hikariConfig);

        var sql = readResourceFile("schema.sql");
        try (var connection = BaseRepository.getConnection();
             var statement = connection.createStatement()
        ) {
            statement.execute(sql);
        }

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(Routes.ROOT_PATH, UrlsController::index);

        app.get(Routes.URLS_PATH, UrlsController::readAll);
        app.get(Routes.URL_PATH, UrlsController::readById);
        app.post(Routes.URLS_PATH, UrlsController::create);

        app.post(Routes.URLS_CHECKS_PATH, UrlChecksController::create);

        return app;
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("DB_PORT", "7070");
        return Integer.parseInt(port);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}
