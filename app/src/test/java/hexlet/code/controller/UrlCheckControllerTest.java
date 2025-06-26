package hexlet.code.controller;

import hexlet.code.App;
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
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UrlCheckControllerTest {

    private static MockWebServer mockServer;
    private Javalin app;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockServer = new MockWebServer();
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
        UrlRepository.save("https://example.com");
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "urlId=1&statusCode=200&h1=h1&title=title&description=description";
            var response = client.post(Routes.urlCheckPath(1), requestBody);
            var responseBody = Objects.requireNonNull(response.body()).string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("1");
            assertThat(responseBody).contains("200");
            assertThat(responseBody).contains("h1");
            assertThat(responseBody).contains("title");
            assertThat(responseBody).contains("description");
        });
    }

    @Test
    public void mockServerTest() throws SQLException {
        MockResponse response = new MockResponse().setBody(HTML).setResponseCode(200);
        mockServer.enqueue(response);

        String fakeUrl = mockServer.url("/").toString();
        UrlRepository.save(fakeUrl);

        JavalinTest.test(app, (server, client) -> {
            var checkResponse = client.post(Routes.urlCheckPath(1));
            var body = Objects.requireNonNull(checkResponse.body()).string();
            assertThat(checkResponse.code()).isEqualTo(200);
            assertThat(body).contains("Test Title");
            assertThat(body).contains("Test H1");
            assertThat(body).contains("Test description");
        });
    }

    public static final String HTML = """
                <html>
                  <head>
                    <title>Test Title</title>
                    <meta name="description" content="Test description">
                  </head>
                  <body>
                    <h1>Test H1</h1>
                  </body>
                </html>
                """;

}

