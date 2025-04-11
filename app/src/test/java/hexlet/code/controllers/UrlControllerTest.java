package hexlet.code.controllers;

import hexlet.code.App;
import hexlet.code.repositories.UrlRepository;
import hexlet.code.utils.Routes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UrlControllerTest {

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
        UrlRepository.saveUrl("https://www.example.com");
        UrlRepository.saveUrlCheck(1, 200, "h1", "title", "description");
    }

    @Test
    public void readSearchFormTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(Routes.ROOT_PATH);
            assertThat(response.code()).isEqualTo(200);
            assertThat(Objects.requireNonNull(response.body()).string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void readUrlsTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(Routes.URLS_PATH);
            var responseBody = Objects.requireNonNull(response.body()).string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("https://www.example.com");
            assertThat(responseBody).contains("200");
        });
    }

    @Test
    public void readUrlTest() {
        JavalinTest.test(app, (server, client) -> {

            var response = client.get(Routes.urlPath(1));
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
    public void createUrlTest() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "name=https://www.example.com";
            var response = client.post(Routes.URLS_PATH, requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(Objects.requireNonNull(response.body()).string()).contains("https://www.example.com");
        });
    }

    @Test
    public void createUrlCheckTest() {
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
    public void checkUrlWithMockServerTest() throws SQLException {
        String html = "<html><head><title>Mock Title</title><meta name=\"description\" content=\"Mock Description\"></head>"
                + "<body><h1>Mock H1</h1></body></html>";

        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "text/html")
                .setBody(html));

        String mockUrl = mockServer.url("/").toString();

        JavalinTest.test(app, (server, client) -> {
            // Добавляем URL
            var addResponse = client.post(Routes.URLS_PATH, "name=" + mockUrl);
            assertThat(addResponse.code()).isEqualTo(200);

            // Ждём, пока URL точно появится в базе
            var maybeUrl = UrlRepository.readUrlByName(mockUrl);
            assertThat(maybeUrl).isPresent(); // теперь безопасно

            int urlId = maybeUrl.get().getId();

            // Выполняем проверку
            var checkResponse = client.post(Routes.urlCheckPath(urlId));
            assertThat(checkResponse.code()).isEqualTo(200);

            // Проверяем наличие данных на странице
            var urlPage = Objects.requireNonNull(client.get(Routes.urlPath(urlId)).body()).string();
            assertThat(urlPage).contains("Mock Title");
            assertThat(urlPage).contains("Mock H1");
            assertThat(urlPage).contains("Mock Description");
            assertThat(urlPage).contains("200");
        });
    }


}
