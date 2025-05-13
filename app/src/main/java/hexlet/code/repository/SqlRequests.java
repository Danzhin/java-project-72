package hexlet.code.repository;

class SqlRequests {

    static final String LAUNCH = """
        DROP TABLE IF EXISTS urls CASCADE;
        DROP TABLE IF EXISTS url_checks;

        CREATE TABLE urls (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255),
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

    static final String SAVE_URL = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

    static final String SAVE_URL_CHECK = """
        INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    static final String READ_URL_BY_ID = "SELECT * FROM urls WHERE id = ?";

    static final String READ_URL_BY_NAME = "SELECT * FROM urls WHERE name = ?";

    static final String READ_URL_CHECKS = "SELECT * FROM url_checks WHERE url_id = ?";

    static final String READ_URLS_WITH_LATEST_CHECKS = """
        SELECT
        urls.id, urls.name,
        url_checks.*
        FROM urls
        LEFT JOIN (
            SELECT
                url_checks.id, url_checks.status_code, url_checks.h1,
                url_checks.title, url_checks.description, url_checks.created_at,
                url_checks.url_id,
                ROW_NUMBER() OVER (PARTITION BY url_checks.url_id ORDER BY url_checks.created_at DESC) AS rn
            FROM url_checks
        ) AS url_checks
        ON urls.id = url_checks.url_id AND url_checks.rn = 1;
        """;

}
