package eu.luminis.workshop.smallsteps.logic.domainService.state

import eu.luminis.workshop.smallsteps.logic.domainModel.LegoParts
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoBuilderId
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber

data class StockState(
    val availableStock: Map<LegoSetNumber, Int> = emptyMap(),
    val reserved: Set<Pair<LegoSetNumber, LegoBuilderId>> = emptySet(),
    val atBuilder: List<Pair<LegoSetNumber, LegoBuilderId>> = emptyList(),
    val incompleteStock: List<LegoBox> = emptyList(),
    val incompleteReturnHistory: List<IncompleteReturn> = emptyList(),
)

data class LegoBox(
    val legoSet: LegoSetNumber,
    val boxNumber: Int,
    val missingParts: LegoParts,
)

data class IncompleteReturn(
    val legoSet: LegoSetNumber,
    val missingParts: LegoParts,
)

fun buildLegoBox(legoSet: LegoSetNumber, boxNumber: Int, missingParts: Map<String, Int>) =
    LegoBox(legoSet, boxNumber, LegoParts(missingParts))

fun buildIncompleteReturn(legoSet: LegoSetNumber, missingParts: Map<String, Int>) =
    IncompleteReturn(legoSet, LegoParts(missingParts))