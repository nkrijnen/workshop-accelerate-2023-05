package eu.luminis.workshop.smallsteps.logic.domainservice;

import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AccessDeniedException;
import eu.luminis.workshop.smallsteps.logic.domainservice.helper.SetupLegoTestApp;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.LegoSetForBuilder;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import eu.luminis.workshop.smallsteps.persistence.InMemoryLegoPartCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class LegoStockReserveTest {
    SetupLegoTestApp app;

    @BeforeEach
    void setUp() {
        app = new SetupLegoTestApp();
    }

    @Test
    public void should_reserve_lego_set_when_in_stock() {
        StockState initialState = new StockState(
                Map.of(app.millenniumFalcon, 1),
                null,
                null,
                null,
                null
        );
        LegoStock legoStock = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), initialState);

        StockState newState = legoStock.reserve(app.millenniumFalcon);

        assertThat(newState.getAvailableStock()).isNotEmpty().contains(entry(app.millenniumFalcon, 0));
        assertThat(newState.getReserved()).isNotEmpty().contains(new LegoSetForBuilder(app.millenniumFalcon, app.harry));
        assertThat(newState.getAtBuilder()).isEqualTo(initialState.getAtBuilder());
        assertThat(newState.getIncompleteStock()).isEqualTo(initialState.getIncompleteStock());
        assertThat(newState.getIncompleteReturnHistory()).isEqualTo(initialState.getIncompleteReturnHistory());
    }

    @Test
    public void should_fail_to_reserve_when_lego_set_not_in_store() {
        LegoStock legoStock = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), new StockState());

        assertThatThrownBy(() -> {
            legoStock.reserve(app.millenniumFalcon);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LegoSet " + app.millenniumFalcon.getNumber() + " is not in stock");
    }

    @Test
    public void should_fail_to_reserve_when_lego_set_0_in_store() {
        StockState initialState = new StockState(
                Map.of(app.millenniumFalcon, 0),
                null,
                null,
                null,
                null
        );
        LegoStock legoStock = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            legoStock.reserve(app.millenniumFalcon);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LegoSet " + app.millenniumFalcon.getNumber() + " is not in stock");
    }

    @Test
    public void should_fail_to_reserve_when_not_authenticated_as_builder() {
        StockState initialState = new StockState(
                Map.of(app.millenniumFalcon, 1),
                null,
                null,
                null,
                null
        );
        LegoStock legoStock = new LegoStock(app.bussumAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            legoStock.reserve(app.millenniumFalcon);
        }).isInstanceOf(AccessDeniedException.class);

    }
}
