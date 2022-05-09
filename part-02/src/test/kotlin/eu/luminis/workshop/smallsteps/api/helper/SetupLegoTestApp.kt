package eu.luminis.workshop.smallsteps.api.helper

import eu.luminis.workshop.smallsteps.LegoRentingContext
import eu.luminis.workshop.smallsteps.legoRentingKtorModule
import eu.luminis.workshop.smallsteps.logic.appService.LegoStockRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

internal fun ApplicationTestBuilder.setupLegoTestApp(stockRepository: LegoStockRepository): HttpClient {
    application {
        legoRentingKtorModule(LegoRentingContext(stockRepository,))
    }

    return createClient {
        install(ContentNegotiation) {
            json()
        }
    }
}