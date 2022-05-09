package eu.luminis.workshop.smallsteps.logic.appService

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState

interface LegoStockRepository {
    fun find(store: LegoStoreId): StockState
    fun save(store: LegoStoreId, newState: StockState)
}