package eu.luminis.workshop.smallsteps.logic.domainservice;

import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AccessDeniedException;
import eu.luminis.workshop.smallsteps.logic.domainservice.helper.SetupLegoTestApp;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.LegoBox;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.LegoSetForBuilder;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import eu.luminis.workshop.smallsteps.persistence.InMemoryLegoPartCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LegoStockReturningTest {
    SetupLegoTestApp app;

    @BeforeEach
    void setUp() {
        app = new SetupLegoTestApp();
    }

    @Test
    public void should_register_returning_complete_lego_set() {
        StockState initialState = new StockState(
                null,
                null,
                List.of(new LegoSetForBuilder(app.millenniumFalcon, app.harry)),
                null,
                null
        );
        LegoStock handler = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), initialState);

        StockState newState = handler.returnComplete(app.millenniumFalcon);

        assertThat(newState.getAtBuilder()).isEqualTo(newState.getAtBuilder());
    }

    @Test
    public void should_fail_to_register_returning_lego_set_when_not_at_builder() {
        StockState initialState = new StockState(
                null,
                null,
                Collections.emptyList(),
                null,
                null
        );
        LegoStock handler = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            handler.returnComplete(app.millenniumFalcon);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot return " + app.millenniumFalcon.getNumber() + " as builder " +
                        app.harry.toString() + " does not have it.");

        assertThatThrownBy(() -> {
            handler.returnInComplete(new LegoBox(app.millenniumFalcon, 42, app.missingParts));
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot return " + app.millenniumFalcon.getNumber() + " as builder " +
                        app.harry.toString() + " does not have it.");

    }

    @Test
    public void should_fail_to_register_returning_lego_set_when_not_at_builder_but_is_at_different_builder() {
        StockState initialState = new StockState(
                null,
                null,
                List.of(new LegoSetForBuilder(app.millenniumFalcon, app.sally)),
                null,
                null
        );
        LegoStock handler = new LegoStock(app.harryAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            handler.returnComplete(app.millenniumFalcon);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot return " + app.millenniumFalcon.getNumber() + " as builder " +
                        app.harry.toString() + " does not have it.");

        assertThatThrownBy(() -> {
            handler.returnInComplete(new LegoBox(app.millenniumFalcon, 42, app.missingParts));
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot return " + app.millenniumFalcon.getNumber() + " as builder " +
                        app.harry.toString() + " does not have it.");
    }

    @Test
    public void should_fail_to_register_returning_lego_set_when_not_autehnticated_as_builder() {
        StockState initialState = new StockState(
                null,
                null,
                List.of(new LegoSetForBuilder(app.millenniumFalcon, app.harry)),
                null,
                null
        );
        LegoStock handler = new LegoStock(app.bussumAuth, new InMemoryLegoPartCatalog(), initialState);

        assertThatThrownBy(() -> {
            handler.returnComplete(app.millenniumFalcon);
        }).isInstanceOf(AccessDeniedException.class);

        assertThatThrownBy(() -> {
            handler.returnInComplete(new LegoBox(app.millenniumFalcon, 42, app.missingParts));
        }).isInstanceOf(AccessDeniedException.class);
    }
}
