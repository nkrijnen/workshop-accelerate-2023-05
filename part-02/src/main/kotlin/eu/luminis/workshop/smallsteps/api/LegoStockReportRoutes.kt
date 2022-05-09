package eu.luminis.workshop.smallsteps.api

import eu.luminis.workshop.smallsteps.logic.appService.LegoStockQueries
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Defines the REST API for stock reports
fun Route.legoStockReportRoutes(legoStockQueries: LegoStockQueries) {
    route("/stock/report") {

        route("/current-missing-parts") {
            get {
                call.request.headers.toAuthProvider().currentAuthentication.mustBeStore()
                val storeId = call.request.headers["X-SELECTED-LEGO-STORE"].toStoreId()

                val report = legoStockQueries.currentlyMissingPartsReport(storeId)
                call.respond(report)
            }
        }

        route("/historicly-most-lost") {
            get {
                call.request.headers.toAuthProvider().currentAuthentication.mustBeStore()
                val storeId = call.request.headers["X-SELECTED-LEGO-STORE"].toStoreId()

                val report = legoStockQueries.historicallyMostLostParts(storeId)
                call.respond(report)
            }
        }

    }
}