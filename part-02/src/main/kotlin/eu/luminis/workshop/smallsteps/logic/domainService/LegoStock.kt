package eu.luminis.workshop.smallsteps.logic.domainService

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoBuilderId
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AuthProvider
import eu.luminis.workshop.smallsteps.logic.domainService.catalog.LegoPartCatalog
import eu.luminis.workshop.smallsteps.logic.domainService.state.IncompleteReturn
import eu.luminis.workshop.smallsteps.logic.domainService.state.LegoBox
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState

data class LegoStock(
    private val authProvider: AuthProvider,
    private val legoPartCatalog: LegoPartCatalog,
    private val state: StockState,
) {
    fun reserve(legoSet: LegoSetNumber): StockState {
        val forBuilder = authProvider.currentAuthentication.requiredBuilderId()

        val inStock = state.availableStock.getOrDefault(legoSet, 0)
        require(inStock > 0) { "LegoSet $legoSet is not in stock" }

        return state.copy(
            availableStock = state.availableStock.mapValues(reduceStockByOne(legoSet)),
            reserved = state.reserved + (legoSet to forBuilder)
        )
    }

    private fun reduceStockByOne(legoSet: LegoSetNumber) = { entry: Map.Entry<LegoSetNumber, Int> ->
        if (entry.key == legoSet) entry.value - 1
        else entry.value
    }

    fun ship(legoSet: LegoSetNumber, toBuilder: LegoBuilderId): StockState {
        authProvider.currentAuthentication.mustBeStore()
        check(state.reserved.contains(legoSet to toBuilder)) { "Legoset $legoSet has not been reserved by builder $toBuilder" }

        return state.copy(
            reserved = state.reserved - (legoSet to toBuilder),
            atBuilder = state.atBuilder + (legoSet to toBuilder),
        )
    }

    fun returningComplete(legoSet: LegoSetNumber): StockState {
        val byBuilder = authProvider.currentAuthentication.requiredBuilderId()
        check(state.atBuilder.contains(legoSet to byBuilder))

        return state
    }

    fun returningInComplete(legoBox: LegoBox): StockState {
        val byBuilder = authProvider.currentAuthentication.requiredBuilderId()
        check(state.atBuilder.contains(legoBox.legoSet to byBuilder))
        legoBox.requireValidMissingLegoParts()

        return state.copy(
            incompleteStock = state.incompleteStock + legoBox,
            incompleteReturnHistory = state.incompleteReturnHistory + IncompleteReturn(
                legoBox.legoSet,
                legoBox.missingParts
            ),
        )
    }

    private fun LegoBox.requireValidMissingLegoParts() {
        require(this.missingParts.isNotEmpty()) { "No missing parts specified" }
        require(this.missingParts.all { it.value > 0 }) { "You must specify how many parts are missing" }

        val expectedParts = legoPartCatalog.allPartsForLegoSet(legoSet)
            ?: throw IllegalStateException("Unable to determine parts for lego set $legoSet")
        require(this.missingParts.all { expectedParts.containsKey(it.key) }) {
            "Some parts reported missing don't belong to lego set $legoSet"
        }
        require(this.missingParts.all { expectedParts.getOrDefault(it.key, 0) >= it.value }) {
            "Too many parts reported missing, the original lego set $legoSet did not contain that many of this part"
        }
    }
}