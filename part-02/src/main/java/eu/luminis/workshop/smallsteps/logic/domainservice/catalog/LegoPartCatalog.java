package eu.luminis.workshop.smallsteps.logic.domainservice.catalog;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoSetNumber;

import java.util.Map;

public interface LegoPartCatalog {
    Map<String, Integer> allPartsForLegoSet(LegoSetNumber legoSetNumber);
}
