package eu.luminis.workshop.smallsteps.api

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoBuilderId
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AccessDeniedException
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AuthProvider
import eu.luminis.workshop.smallsteps.logic.domainService.auth.LegoBuilderAuthentication
import eu.luminis.workshop.smallsteps.logic.domainService.auth.LegoStoreAuthentication
import io.ktor.http.*

// Understands how to interpret HTTP authentication headers
internal fun Headers.toAuthProvider(): AuthProvider {
    val gatewayAuth = this["X-API-GATEWAY-AUTH"] ?: throw AccessDeniedException()
    if (gatewayAuth.startsWith(LegoBuilderId.PREFIX)) return object : AuthProvider {
        override val currentAuthentication = LegoBuilderAuthentication(LegoBuilderId(gatewayAuth))
    }
    if (gatewayAuth.startsWith(LegoStoreId.PREFIX)) return object : AuthProvider {
        override val currentAuthentication = LegoStoreAuthentication(LegoStoreId(gatewayAuth))
    }
    throw AccessDeniedException()
}