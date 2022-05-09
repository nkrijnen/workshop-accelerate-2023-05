package eu.luminis.workshop.smallsteps.persistence

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber
import eu.luminis.workshop.smallsteps.logic.domainService.catalog.LegoPartCatalog

class InMemoryLegoPartCatalog(private val partsForLegoSets: LegoPartLookup) : LegoPartCatalog {
    override fun allPartsForLegoSet(legoSet: LegoSetNumber): Map<String, Int>? = partsForLegoSets[legoSet]
}

typealias LegoPartLookup = Map<LegoSetNumber, Map<String, Int>>