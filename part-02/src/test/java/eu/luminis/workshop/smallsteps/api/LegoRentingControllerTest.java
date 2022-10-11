package eu.luminis.workshop.smallsteps.api;

import eu.luminis.workshop.smallsteps.helpers.TestLegoStockRepository;
import eu.luminis.workshop.smallsteps.logic.domainservice.helper.SetupLegoTestApp;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.LegoSetForBuilder;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class LegoRentingControllerTest {
    SetupLegoTestApp config;

    @Inject
    TestLegoStockRepository repository;

    @BeforeEach
    void setUp() {
        config = new SetupLegoTestApp();

        repository.setInitialState(new StockState(
                        Map.of(config.millenniumFalcon, 3),
                        null,
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    public void should_handle_typical_reserve_and_ship_flow() {
        given()
                .header("X-API-GATEWAY-AUTH", config.harry.toString())
                .header("X-SELECTED-LEGO-STORE", config.bussum.toString())
                .when()
                .post("/renting/legoset/{legoSetId}/reserve", config.millenniumFalcon.getNumber())
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .body(is("Lego set has been reserved"));

        repository.assertStockState(
                new StockState(
                        Map.of(config.millenniumFalcon, 2),
                        Set.of(new LegoSetForBuilder(config.millenniumFalcon, config.harry)),
                        null,
                        null,
                        null
                )

        );

        given()
                .header("X-API-GATEWAY-AUTH", config.bussum.toString())
                .header("X-SELECTED-LEGO-STORE", config.bussum.toString())
                .when()
                .post("/renting/legoset/{legoSetId}/ship/{builderId}",
                        config.millenniumFalcon.getNumber(), config.harry.toString())
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .body(is("Shipment registered"));

        repository.assertStockState(
                new StockState(
                        Map.of(config.millenniumFalcon, 2),
                        Collections.emptySet(),
                        List.of(new LegoSetForBuilder(config.millenniumFalcon, config.harry)),
                        null,
                        null
                )

        );
    }
}
