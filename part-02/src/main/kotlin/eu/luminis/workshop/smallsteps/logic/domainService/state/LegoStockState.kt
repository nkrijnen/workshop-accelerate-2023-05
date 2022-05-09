package eu.luminis.workshop.smallsteps.logic.domainService.state

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
    val missingParts: Map<String, Int>,
)

data class IncompleteReturn(
    val legoSet: LegoSetNumber,
    val missingParts: Map<String, Int>,
)