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
            var responseBody = response.body().string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("Анализатор страниц");
        });
    }

    @Test
    public void readAllTest() {
        JavalinTest.test(app, (server, client) -> {
            var urlId = UrlRepository.save(TestUtils.TEST_URL);
            UrlCheckRepository.save(urlId, 200, "title", "h1", "description");
            var response = client.get(Routes.URLS_PATH);
            var responseBody = response.body().string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains(TestUtils.TEST_URL);
            assertThat(responseBody).contains("200");
        });
    }

    @Test
    public void readByIdTest() {
        JavalinTest.test(app, (server, client) -> {
            var urlId = UrlRepository.save(TestUtils.TEST_URL);
            UrlCheckRepository.save(urlId, 200, "title", "h1", "description");
            var response = client.get(Routes.urlPath(urlId));
            var responseBody = response.body().string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains(TestUtils.TEST_URL);
            assertThat(responseBody).contains("200");
            assertThat(responseBody).contains("title");
            assertThat(responseBody).contains("h1");
            assertThat(responseBody).contains("description");
        });
    }

    @Test
    public void createTest() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + TestUtils.TEST_URL;
            var postResponse = client.post(Routes.URLS_PATH, requestBody);
            assertThat(postResponse.code()).isEqualTo(200);

            var getResponse = client.get(Routes.URLS_PATH);
            assertThat(getResponse.code()).isEqualTo(200);
            assertThat(getResponse.body().string()).contains(TestUtils.TEST_URL);
        });
    }

}

