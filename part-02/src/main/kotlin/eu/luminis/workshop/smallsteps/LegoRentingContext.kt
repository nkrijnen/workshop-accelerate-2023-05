package eu.luminis.workshop.smallsteps

import eu.luminis.workshop.smallsteps.logic.appService.LegoStockMutation
import eu.luminis.workshop.smallsteps.logic.appService.LegoStockQueries
import eu.luminis.workshop.smallsteps.logic.appService.LegoStockRepository
import eu.luminis.workshop.smallsteps.logic.domainService.catalog.LegoPartCatalog
import eu.luminis.workshop.smallsteps.persistence.InMemoryLegoPartCatalog
import eu.luminis.workshop.smallsteps.persistence.InMemoryLegoStockRepository

// Understands how to wire up the various components in the system
internal class LegoRentingContext(
    legoStockRepository: LegoStockRepository = InMemoryLegoStockRepository(),
    legoPartCatalog: LegoPartCatalog = InMemoryLegoPartCatalog(emptyMap())
) {
    val legoStockMutationService = LegoStockMutation(legoStockRepository, legoPartCatalog)
    val legoStockQueryService = LegoStockQueries(legoStockRepository)
}