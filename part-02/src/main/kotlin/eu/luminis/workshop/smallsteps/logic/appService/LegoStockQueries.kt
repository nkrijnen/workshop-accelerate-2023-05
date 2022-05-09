package eu.luminis.workshop.smallsteps.logic.appService

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId

class LegoStockQueries(private val repository: LegoStockRepository) {
    fun currentlyMissingPartsReport(store: LegoStoreId): List<Pair<String, Int>> {
        val stockState = repository.find(store)
        return stockState.incompleteStock
            .map { it.missingParts }
            .mergeAndSum()
            .toSortedListOfPairs()
    }

    fun historicallyMostLostParts(store: LegoStoreId): List<Pair<String, Int>> {
        val stockState = repository.find(store)
        return stockState.incompleteReturnHistory
            .map { it.missingParts }
            .mergeAndSum()
            .toSortedListOfPairs()
    }
}

private fun List<Map<String, Int>>.mergeAndSum(): Map<String, Int> {
    return this.fold(mapOf(), ::mergePartsAndSumValues)
}

fun mergePartsAndSumValues(parts: Map<String, Int>, otherParts: Map<String, Int>): Map<String, Int> {
    val allPartCounts = parts.asSequence() + otherParts.asSequence()
    val groupedByPartNr: Map<String, List<Int>> = allPartCounts.groupBy({ it.key }, { it.value })
    return groupedByPartNr.mapValues { (_, values) -> values.sum() }
}

private fun Map<String, Int>.toSortedListOfPairs(): List<Pair<String, Int>> {
    return this.toList().sortedBy { it.first }.sortedByDescending { it.second }
}
