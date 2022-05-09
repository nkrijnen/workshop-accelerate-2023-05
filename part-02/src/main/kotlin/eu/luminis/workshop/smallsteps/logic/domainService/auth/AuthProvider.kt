package eu.luminis.workshop.smallsteps.logic.domainService.auth

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoBuilderId
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId

interface AuthProvider {
    val currentAuthentication: Authentication
}

sealed interface Authentication {
    fun requiredBuilderId() = when (this) {
        is LegoBuilderAuthentication -> this.builder
        else -> throw AccessDeniedException()
    }

    fun mustBeStore() {
        if (this !is LegoStoreAuthentication) throw AccessDeniedException()
    }
}

data class LegoBuilderAuthentication(val builder: LegoBuilderId) : Authentication

data class LegoStoreAuthentication(val storeId: LegoStoreId) : Authentication

class AccessDeniedException : RuntimeException()