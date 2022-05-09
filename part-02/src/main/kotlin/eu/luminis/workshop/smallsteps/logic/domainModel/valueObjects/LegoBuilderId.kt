package eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects

import java.util.*

data class LegoBuilderId(val id: UUID = UUID.randomUUID()) {
    constructor(builderId: String) : this(UUID.fromString(builderId.removePrefix(PREFIX)))

    override fun toString() = "$PREFIX$id"

    companion object {
        const val PREFIX = "bldr_"
    }
}