package eu.luminis.workshop.smallsteps.persistence;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoSetNumber;
import eu.luminis.workshop.smallsteps.logic.domainservice.catalog.LegoPartCatalog;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class InMemoryLegoPartCatalog implements LegoPartCatalog {
    private final Map<LegoSetNumber, Map<String, Integer>> partsForLegoSets;

    public InMemoryLegoPartCatalog() {
        this(null);
    }

    public InMemoryLegoPartCatalog(Map<LegoSetNumber, Map<String, Integer>> partsForLegoSets) {
        this.partsForLegoSets = partsForLegoSets;
    }

    @Override
    public Map<String, Integer> allPartsForLegoSet(LegoSetNumber legoSetNumber) {
        return partsForLegoSets.getOrDefault(legoSetNumber, Collections.emptyMap());
    }
}
