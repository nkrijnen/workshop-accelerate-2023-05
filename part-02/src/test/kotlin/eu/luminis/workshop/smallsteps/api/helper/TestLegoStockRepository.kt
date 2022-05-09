package eu.luminis.workshop.smallsteps.api.helper

import eu.luminis.workshop.smallsteps.logic.appService.LegoStockRepository
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import org.assertj.core.api.Assertions

internal class TestLegoStockRepository(initialState: StockState) : LegoStockRepository {
    private var currentState = initialState

    val stockState: StockState get() = currentState

    override fun find(store: LegoStoreId) = currentState

    override fun save(store: LegoStoreId, newState: StockState) {
        currentState = newState
    }

    fun assertStockState(expected: StockState) {
        Assertions.assertThat(stockState).isEqualTo(expected)
    }
}