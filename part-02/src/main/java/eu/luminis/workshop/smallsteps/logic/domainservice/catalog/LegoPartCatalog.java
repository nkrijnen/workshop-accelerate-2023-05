package eu.luminis.workshop.smallsteps.logic.domainservice.catalog;

import eu.luminis.workshop.smallsteps.logic.domainmodel.LegoParts;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoSetNumber;

public interface LegoPartCatalog {
    LegoParts allPartsForLegoSet(LegoSetNumber legoSetNumber);
}
