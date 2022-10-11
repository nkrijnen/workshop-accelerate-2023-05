package eu.luminis.workshop.smallsteps.persistence;

import eu.luminis.workshop.smallsteps.logic.appservice.LegoStockRepository;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryLegoStockRepository  implements LegoStockRepository {
    private final Map<LegoStoreId, StockState> data;

    public InMemoryLegoStockRepository() {
        data = new HashMap<>();
    }

    @Override
    public StockState find(LegoStoreId legoStoreId) {
        return data.getOrDefault(legoStoreId, new StockState());
    }

    @Override
    public void save(LegoStoreId legoStoreId, StockState newState) {
        data.put(legoStoreId, newState);
    }
}
