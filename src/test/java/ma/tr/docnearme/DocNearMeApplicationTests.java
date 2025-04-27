package ma.tr.docnearme;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        // Application properties
        "spring.application.name=DocNearMe-Test",

        // Mail properties
        "spring.mail.host=localhost",
        "spring.mail.port=587",
        "spring.mail.username=test@docnearme.com",
        "spring.mail.password=testpassword",
        "spring.mail.properties.mail.smtp.auth=false",
        "spring.mail.properties.mail.smtp.starttls.enable=false",

        // Database properties (using H2 for tests)
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.hikari.maximum-pool-size=5",

        // JPA properties
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.format_sql=true",

        // Security properties
        "security.jwt.secret-key=test-secret-key-1234567890",
        "security.jwt.expiration-time=3600000",

        // API properties
        "api.medication.base-url=http://localhost:8080/mock-api",
        "api.medication.timeout=5000",
        "api.gemini.base-url=http://localhost:8080/mock-gemini",
        "api.gemini.model=test-model",
        "api.gemini.key=test-key",
        "api.gemini.timeout=5000",

        // Logging properties
        "logging.level.root=WARN",
        "logging.level.ma.tr.docnearme=DEBUG",
        "logging.file.name=build/test-logs/test.log"
})
class DocNearMeApplicationTests {

    @Test
    void contextLoads() {
    }

}
