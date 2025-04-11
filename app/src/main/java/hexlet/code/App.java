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
import hexlet.code.repositories.BaseRepository;
import hexlet.code.utils.Routes;
import hexlet.code.controllers.UrlController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        var port = getPort();
        app.start(port);
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        var jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        hikariConfig.setJdbcUrl(jdbcUrl);
        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()
        ) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

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

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

}
