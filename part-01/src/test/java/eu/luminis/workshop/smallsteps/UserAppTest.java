package eu.luminis.workshop.smallsteps;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserAppTest {

    @Test
    public void test_missing_content_type() {
        given()
                .when().post("/user")
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void test_create_user() {
        given()
                .body("{\"email\": \"harry@example.com\", \"password\": \"mysecret\"}")
                .header("Content-Type", "application/json")
                .when().post("/user")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(is("User registered correctly"));
    }

    @Test
    public void test_illegal_password() {
        given()
                .body("{\"email\": \"harry@example.com\", \"password\": \"secret\"}")
                .header("Content-Type", "application/json")
                .when().post("/user")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(is("Password must be at least 8 characters long"));

    }
}
