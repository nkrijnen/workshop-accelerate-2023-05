package eu.luminis.workshop.smallsteps.logic.appService

import eu.luminis.workshop.smallsteps.logic.domainModel.LegoParts
import eu.luminis.workshop.smallsteps.logic.domainModel.toLegoParts
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId

class LegoStockQueries(private val repository: LegoStockRepository) {
    fun currentlyMissingPartsReport(store: LegoStoreId): List<Pair<String, Int>> {
        val stockState = repository.find(store)
        return stockState.incompleteStock
            .map { it.missingParts.toLegoParts() }
            .mergeAndSum()
            .toSortedListOfPairs()
    }

    fun historicallyMostLostParts(store: LegoStoreId): List<Pair<String, Int>> {
        val stockState = repository.find(store)
        return stockState.incompleteReturnHistory
            .map { it.missingParts.toLegoParts() }
            .mergeAndSum()
            .toSortedListOfPairs()
    }
}

private fun List<LegoParts>.mergeAndSum(): LegoParts {
    return this.fold(LegoParts(), LegoParts::plus)
}

private fun LegoParts.toSortedListOfPairs(): List<Pair<String, Int>> {
    return this.toListOfPartCounts().sortedBy { it.first }.sortedByDescending { it.second }
}
