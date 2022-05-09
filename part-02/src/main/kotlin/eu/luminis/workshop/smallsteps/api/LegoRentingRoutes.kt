package eu.luminis.workshop.smallsteps.api

import eu.luminis.workshop.smallsteps.logic.appService.LegoStockMutation
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoBuilderId
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoStoreId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Defines the REST API for renting lego sets
fun Route.legoRentingRoutes(legoStockMutationService: LegoStockMutation) {
    route("/renting/legoset/{legoSetNumber}") {

        route("/reserve") {
            post {
                val auth = call.request.headers.toAuthProvider()
                val storeId = call.request.headers["X-SELECTED-LEGO-STORE"].toStoreId()
                val legoSet = call.parameters["legoSetNumber"].toLegoSetNumber()

                legoStockMutationService.handleMutation(auth, storeId) { stock ->
                    stock.reserve(legoSet)
                }

                call.respondText("Lego set has been reserved", status = HttpStatusCode.NoContent)
            }
        }

        route("/ship/{builder}") {
            post {
                val auth = call.request.headers.toAuthProvider()
                val storeId = call.request.headers["X-SELECTED-LEGO-STORE"].toStoreId()
                val legoSet = call.parameters["legoSetNumber"].toLegoSetNumber()
                val builder = call.parameters["builder"].toBuilderId()

                legoStockMutationService.handleMutation(auth, storeId) { stock ->
                    stock.ship(legoSet, builder)
                }

                call.respondText("Shipment registered", status = HttpStatusCode.NoContent)
            }
        }

    }
}

internal fun String?.toStoreId(): LegoStoreId = this?.let(::LegoStoreId)
    ?: throw IllegalArgumentException("Missing or malformed lego store id")

internal fun String?.toBuilderId(): LegoBuilderId = this?.let(::LegoBuilderId)
    ?: throw IllegalArgumentException("Missing or malformed lego builder id")

internal fun String?.toLegoSetNumber(): LegoSetNumber = this?.toInt()?.let(::LegoSetNumber)
    ?: throw IllegalArgumentException("Missing or malformed lego set number")