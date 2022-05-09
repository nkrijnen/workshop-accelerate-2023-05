package eu.luminis.workshop.smallsteps.persistence

import eu.luminis.workshop.smallsteps.logic.appService.LegoStockRepository
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState

class InMemoryLegoStockRepository : LegoStockRepository {
    private val data = mutableMapOf<LegoStoreId, StockState>()

    override fun find(store: LegoStoreId): StockState {
        return data[store] ?: StockState()
    }

    override fun save(store: LegoStoreId, newState: StockState) {
        data[store] = newState
    }
}

