package eu.luminis.workshop.smallsteps.helpers;

import eu.luminis.workshop.smallsteps.logic.appservice.LegoStockRepository;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Mock
@ApplicationScoped
public class TestLegoStockRepository implements LegoStockRepository {
    private StockState currentState;

    @Override
    public StockState find(LegoStoreId legoStoreId) {
        return this.currentState;
    }

    @Override
    public void save(LegoStoreId legoStoreId, StockState newState) {
        this.currentState = newState;
    }

    public void assertStockState(StockState expected) {
        assertThat(expected.getReserved()).isEqualTo(currentState.getReserved());
    }

    public void setInitialState(StockState initialState) {
        this.currentState = initialState;
    }
}
