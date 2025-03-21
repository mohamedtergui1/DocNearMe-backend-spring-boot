package ma.tr.docnearme;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.mail.username=test@example.com",
        "spring.mail.password=testpassword",
        "security.jwt.secret-key=test-secret-key",
        "medicationApi=http://localhost:8080/mock-api"
})
class DocNearMeApplicationTests {

    @Test
    void contextLoads() {
    }

}
