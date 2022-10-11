package eu.luminis.workshop.smallsteps.logic.appservice;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;

public interface LegoStockRepository {
    StockState find(LegoStoreId legoStoreId);

    void save(LegoStoreId legoStoreId, StockState newState);
}
