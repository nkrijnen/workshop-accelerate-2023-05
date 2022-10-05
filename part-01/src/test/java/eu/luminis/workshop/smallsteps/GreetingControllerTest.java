package eu.luminis.workshop.smallsteps;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingControllerTest {
    /**
     * TODO: First run the the application using the Quarkus dev profile, push "r" to re-start the tests
     * TODO: This test should fail, fix the code in GreetingController to make the test pass
     */
    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/greeting?name=Harry")
                .then()
                .statusCode(200)
                .body(is("Hello Harry!"));
    }
}
