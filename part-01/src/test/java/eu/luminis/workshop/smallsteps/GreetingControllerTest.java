package eu.luminis.workshop.smallsteps;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/greeting?name=test")
                .then()
                .statusCode(200)
                .body(is("hello test!"));
    }
}
