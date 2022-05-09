package eu.luminis.workshop.smallsteps.logic.domainService.catalog

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber

interface LegoPartCatalog {
    fun allPartsForLegoSet(legoSet: LegoSetNumber): Map<String, Int>?
}