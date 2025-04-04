package hexlet.code.utils;

public class SqlRequests {

    public static final String SAVE_URL = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

    public static final String GET_URLS = "SELECT * FROM urls";

    public static final String GET_URL_BY_ID = "SELECT * FROM urls WHERE id = ?";

    public static final String CONTAINS_NAME = "SELECT EXISTS (SELECT 1 FROM urls WHERE name = ?)";

    public static final String GET_URLS_WITH_LATEST_CHECKS = """
        SELECT
        urls.id, urls.name,
        url_checks.*
        FROM urls
        LEFT JOIN (
            SELECT
                url_checks.id, url_checks.status_code, url_checks.h1,
                url_checks.title, url_checks.description, url_checks.created_at,
                url_checks.url_id,  -- добавляем столбец url_id
                ROW_NUMBER() OVER (PARTITION BY url_checks.url_id ORDER BY url_checks.created_at DESC) AS rn
            FROM url_checks
        ) AS url_checks
        ON urls.id = url_checks.url_id AND url_checks.rn = 1;
        """;



    public static final String SAVE_URL_CHECK = """
        INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    public static final String GET_URL_CHECKS = "SELECT * FROM url_checks WHERE url_id = ?";

    public static final String CLEAR = """
        DELETE FROM url_checks;
        DELETE FROM urls;
        """;

}
