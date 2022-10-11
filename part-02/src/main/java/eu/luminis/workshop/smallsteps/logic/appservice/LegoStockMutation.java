package eu.luminis.workshop.smallsteps.logic.appservice;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.LegoStock;
import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AuthProvider;
import eu.luminis.workshop.smallsteps.logic.domainservice.catalog.LegoPartCatalog;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.StockState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegoStockMutation {
    @Autowired
    private LegoStockRepository repository;

    @Autowired
    private LegoPartCatalog catalog;

    public void handleMutation(AuthProvider authProvider, LegoStoreId legoStoreId, MutationHandler mutationHandler) {
        StockState currentState = repository.find(legoStoreId);

        LegoStock legoStock = new LegoStock(authProvider, catalog, currentState);
        StockState newState = mutationHandler.handle(legoStock);

        repository.save(legoStoreId, newState);
    }

    public interface MutationHandler {
        StockState handle(LegoStock stock);
    }

}
