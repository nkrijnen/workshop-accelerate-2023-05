package eu.luminis.workshop.smallsteps.logic.domainservice;

import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AccessDeniedException;
import eu.luminis.workshop.smallsteps.logic.domainservice.helper.SetupLegoTestApp;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.LegoSetForBuilder;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import eu.luminis.workshop.smallsteps.persistence.InMemoryLegoPartCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LegoStockShipTest {
    SetupLegoTestApp app;

    @BeforeEach
    void setUp() {
        app = new SetupLegoTestApp();
    }

    @Test
    public void should_register_shipping_lego_set_when_reserved() {
        StockState initialState = new StockState(
                null,
                Set.of(new LegoSetForBuilder(app.millenniumFalcon, app.harry)),
                null,
                null,
                null
        );
        LegoStock handler = new LegoStock(app.bussumAuth, new InMemoryLegoPartCatalog(), initialState);

        StockState newState = handler.ship(app.millenniumFalcon, app.harry);

        assertThat(newState.getReserved()).isEmpty();
        assertThat(newState.getAtBuilder()).isEqualTo(List.of(new LegoSetForBuilder(app.millenniumFalcon, app.harry)));
        assertThat(newState.getAvailableStock()).isEqualTo(initialState.getAvailableStock());
        assertThat(newState.getIncompleteStock()).isEqualTo(initialState.getIncompleteStock());
        assertThat(newState.getIncompleteReturnHistory()).isEqualTo(initialState.getIncompleteReturnHistory());
    }

    @Test
    public void should_fail_to_ship_when_not_reserved_by_builder() {
        StockState initialState = new StockState(
                null,
                Collections.emptySet(),
                null,
                null,
                null
        );
        LegoStock handler = new LegoStock(app.bussumAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            handler.ship(app.millenniumFalcon, app.harry);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Legoset " + app.millenniumFalcon.getNumber() + " has not been reserved by builder " + app.harry.toString());

    }

    @Test
    public void should_fail_to_ship_when_not_authenticated_as_shop() {
        StockState initialState = new StockState(
                null,
                Set.of(new LegoSetForBuilder(app.millenniumFalcon, app.harry)),
                null,
                null,
                null
        );
        LegoStock handler = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            handler.ship(app.millenniumFalcon, app.harry);
        }).isInstanceOf(AccessDeniedException.class);
    }
}
