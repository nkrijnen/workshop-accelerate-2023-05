package eu.luminis.workshop.smallsteps

import eu.luminis.workshop.smallsteps.api.configureErrorHandling
import eu.luminis.workshop.smallsteps.api.legoRentingRoutes
import eu.luminis.workshop.smallsteps.api.legoStockReportRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

// Understands how to configure a ktor http server for this app
internal fun Application.legoRentingKtorModule(context: LegoRentingContext = LegoRentingContext()) {
    this.configureSerialization()
    this.configureErrorHandling()
    this.configureRouting(context)
}

private fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

private fun Application.configureRouting(context: LegoRentingContext) {
    routing {
        legoRentingRoutes(context.legoStockMutationService)
        legoStockReportRoutes(context.legoStockQueryService)
    }
}