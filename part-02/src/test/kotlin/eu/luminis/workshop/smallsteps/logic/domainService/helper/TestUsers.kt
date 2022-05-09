package eu.luminis.workshop.smallsteps.logic.domainService.helper

import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoBuilderId
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AuthProvider
import eu.luminis.workshop.smallsteps.logic.domainService.auth.LegoBuilderAuthentication
import eu.luminis.workshop.smallsteps.logic.domainService.auth.LegoStoreAuthentication

internal object TestUsers {
    val harry = LegoBuilderId()
    val harryAuth = object : AuthProvider {
        override val currentAuthentication = LegoBuilderAuthentication(harry)
    }

    val sally = LegoBuilderId()

    val bussum = LegoStoreId()
    val bussumAuth = object : AuthProvider {
        override val currentAuthentication = LegoStoreAuthentication(bussum)
    }
}