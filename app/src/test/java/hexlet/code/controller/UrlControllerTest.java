package hexlet.code.controller;

import hexlet.code.App;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UrlControllerTest {

    private Javalin app;

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void indexTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(Routes.ROOT_PATH);
            var responseBody = Objects.requireNonNull(response.body()).string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("Анализатор страниц");
        });
    }

    @Test
    public void readAllTest() {
        JavalinTest.test(app, (server, client) -> {
            var urlId = UrlRepository.save("https://www.example.com");
            UrlCheckRepository.save(urlId, 200, "h1", "title", "description");
            var response = client.get(Routes.URLS_PATH);
            var responseBody = Objects.requireNonNull(response.body()).string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("https://www.example.com");
            assertThat(responseBody).contains("200");
        });
    }

    @Test
    public void readByIdTest() {
        JavalinTest.test(app, (server, client) -> {
            var urlId = UrlRepository.save("https://www.example.com");
            UrlCheckRepository.save(urlId, 200, "h1", "title", "description");
            var response = client.get(Routes.urlPath(urlId));
            var responseBody = Objects.requireNonNull(response.body()).string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("https://www.example.com");
            assertThat(responseBody).contains("200");
            assertThat(responseBody).contains("h1");
            assertThat(responseBody).contains("title");
            assertThat(responseBody).contains("description");
        });
    }

    @Test
    public void createTest() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.comm";
            var response = client.post(Routes.URLS_PATH, requestBody);
            var responseBody = Objects.requireNonNull(response.body()).string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("https://www.example.com");
        });
    }

}

