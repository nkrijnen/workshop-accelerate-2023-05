package eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects

import java.util.*

data class LegoStoreId(val id: UUID = UUID.randomUUID()) {
    constructor(storeId: String) : this(UUID.fromString(storeId.removePrefix(PREFIX)))

    override fun toString() = "$PREFIX$id"

    companion object {
        const val PREFIX = "stor_"
    }
}