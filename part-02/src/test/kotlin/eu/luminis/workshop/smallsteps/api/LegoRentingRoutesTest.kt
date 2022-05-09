package eu.luminis.workshop.smallsteps.api

import eu.luminis.workshop.smallsteps.api.helper.TestLegoStockRepository
import eu.luminis.workshop.smallsteps.api.helper.setupLegoTestApp
import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper.millenniumFalcon
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LegoRentingRoutesTest {
    @Test
    fun `should handle typical reserve and ship flow`() = testApplication {
        // given
        val stockRepository = TestLegoStockRepository(
            StockState(
                availableStock = mapOf(
                    millenniumFalcon to 3
                )
            )
        )
        val client = setupLegoTestApp(stockRepository)

        // when
        client.callReserve()

        // then
        stockRepository.assertStockState(
            StockState(
                availableStock = mapOf(millenniumFalcon to 2),
                reserved = setOf(millenniumFalcon to TestUsers.harry),
            )
        )

        // and when
        client.callShip()

        // then
        stockRepository.assertStockState(
            StockState(
                availableStock = mapOf(millenniumFalcon to 2),
                reserved = emptySet(),
                atBuilder = listOf(millenniumFalcon to TestUsers.harry),
            )
        )
    }
}

private suspend fun HttpClient.callReserve() {
    post("/renting/legoset/$millenniumFalcon/reserve") {
        header("X-API-GATEWAY-AUTH", "${TestUsers.harry}")
        header("X-SELECTED-LEGO-STORE", "${TestUsers.bussum}")
    }.apply {
        assertEquals(HttpStatusCode.NoContent, status)
        assertEquals("Lego set has been reserved", bodyAsText())
    }
}

private suspend fun HttpClient.callShip() {
    post("/renting/legoset/$millenniumFalcon/ship/${TestUsers.harry}") {
        header("X-API-GATEWAY-AUTH", "${TestUsers.bussum}")
        header("X-SELECTED-LEGO-STORE", "${TestUsers.bussum}")
    }.apply {
        assertEquals(HttpStatusCode.NoContent, status)
        assertEquals("Shipment registered", bodyAsText())
    }
}

