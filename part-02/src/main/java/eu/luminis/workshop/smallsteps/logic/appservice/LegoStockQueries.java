package eu.luminis.workshop.smallsteps.logic.appservice;

import eu.luminis.workshop.smallsteps.logic.domainmodel.LegoParts;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LegoStockQueries {
    private final LegoStockRepository repository;

    @Autowired
    public LegoStockQueries(LegoStockRepository repository) {
        this.repository = repository;
    }

    public LegoParts currentlyMissingPartsReport(LegoStoreId legoStoreId) {
        StockState foundState = repository.find(legoStoreId);

        LegoParts summed = new LegoParts(Map.of());
        for(LegoBox legoBox:foundState.getIncompleteStock()) {
            summed = summed.plus(legoBox.getMissingParts());
        }

        return summed;
    }

    public LegoParts historicallyMostLostParts(LegoStoreId legoStoreId) {
        StockState foundState = repository.find(legoStoreId);

        List<LegoParts> collect = foundState.getIncompleteReturnHistory().stream()
                .map(IncompleteReturn::getMissingParts)
                .collect(Collectors.toList());

        LegoParts summed = new LegoParts(Map.of());
        for(LegoParts parts: collect) {
            summed = summed.plus(parts);
        }

        return summed;
    }
}
