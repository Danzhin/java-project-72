package hexlet.code;

import java.sql.SQLException;

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

    public static void main(String[] args) throws SQLException {
        var app = getApp();
        var port = getPort();
        app.start(port);
    }

    public static Javalin getApp() throws SQLException {
        BaseRepository.dataSource = initDataSource();

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

    private static HikariDataSource initDataSource() throws SQLException {
        var jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()
        ) {
            statement.execute(SQL_INIT_DATA_SOURCE);
        }
        return dataSource;
    }

    private static final String SQL_INIT_DATA_SOURCE = """
            DROP TABLE IF EXISTS urls CASCADE;
            DROP TABLE IF EXISTS url_checks;

            CREATE TABLE urls (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) UNIQUE,
                created_at TIMESTAMP
            );

            CREATE TABLE url_checks (
                id SERIAL PRIMARY KEY,
                url_id INTEGER REFERENCES urls(id),
                status_code INTEGER,
                h1 VARCHAR(255),
                title VARCHAR(255),
                description TEXT,
                created_at TIMESTAMP
            );
            """;

}
