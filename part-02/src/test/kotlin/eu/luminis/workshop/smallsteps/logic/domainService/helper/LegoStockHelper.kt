package eu.luminis.workshop.smallsteps.logic.domainService.helper

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber
import eu.luminis.workshop.smallsteps.logic.domainService.LegoStock
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AuthProvider
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import eu.luminis.workshop.smallsteps.persistence.InMemoryLegoPartCatalog
import eu.luminis.workshop.smallsteps.persistence.LegoPartLookup

internal object LegoStockHelper {
    fun build(
        state: StockState,
        auth: AuthProvider = TestUsers.harryAuth,
        partCatalog: LegoPartLookup = emptyMap(),
    ) = LegoStock(
        authProvider = auth,
        legoPartCatalog = InMemoryLegoPartCatalog(partCatalog),
        state = state,
    )

    val millenniumFalcon = LegoSetNumber(75192)
}