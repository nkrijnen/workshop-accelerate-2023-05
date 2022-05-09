package eu.luminis.workshop.smallsteps.logic.appService

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import eu.luminis.workshop.smallsteps.logic.domainService.LegoStock
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AuthProvider
import eu.luminis.workshop.smallsteps.logic.domainService.catalog.LegoPartCatalog
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState

class LegoStockMutation(private val repository: LegoStockRepository, private val legoPartCatalog: LegoPartCatalog) {
    fun handleMutation(
        auth: AuthProvider,
        storeId: LegoStoreId,
        mutationBlock: (LegoStock) -> StockState
    ) {
        val currentState: StockState = repository.find(storeId)

        val legoStock = LegoStock(auth, legoPartCatalog, currentState)
        val newState = mutationBlock(legoStock)

        repository.save(storeId, newState)
    }
}