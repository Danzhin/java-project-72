package hexlet.code.controller;

import hexlet.code.App;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.Routes;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static hexlet.code.controller.TestUtils.readFixture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UrlCheckControllerTest {

    private static MockWebServer mockServer;
    private Javalin app;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        var html = readFixture("index.html");
        MockResponse response = new MockResponse().setBody(html).setResponseCode(200);
        mockServer.enqueue(response);
        mockServer.start();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void createTest() throws SQLException {
        var urlId = UrlRepository.save(TestUtils.TEST_URL);
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "urlId=1&statusCode=200&h1=h1&title=title&description=description";
            var postResponse = client.post(Routes.urlCheckPath(urlId), requestBody);
            assertThat(postResponse.code()).isEqualTo(200);

            var getResponse = client.get(Routes.urlPath(urlId));
            var responseBody = getResponse.body().string();
            assertThat(getResponse.code()).isEqualTo(200);
            assertThat(responseBody).contains("1");
            assertThat(responseBody).contains("200");
            assertThat(responseBody).contains("h1");
            assertThat(responseBody).contains("title");
            assertThat(responseBody).contains("description");
        });
    }

    @Test
    public void mockServerTest() throws SQLException, IOException {
        var testUrl = mockServer.url("/").toString();
        var urlId = UrlRepository.save(testUrl);

        JavalinTest.test(app, (server, client) -> {
            var postResponse = client.post(Routes.urlCheckPath(urlId));
            assertThat(postResponse.code()).isEqualTo(200);

            var getResponse = client.get(Routes.urlPath(urlId));
            assertThat(getResponse.code()).isEqualTo(200);

            var body = getResponse.body().string();
            var urlCheck = UrlCheckRepository.readAll(urlId).getFirst();
            assertThat(body).contains("Test Title");
            assertThat(body).contains("Test H1");
            assertThat(body).contains("Test description");
            assertThat(urlCheck.getTitle()).contains("Test Title");
            assertThat(urlCheck.getH1()).contains("Test H1");
            assertThat(urlCheck.getDescription()).contains("Test description");
        });
    }

}

