package eu.luminis.workshop.smallsteps.api

import eu.luminis.workshop.smallsteps.api.helper.TestLegoStockRepository
import eu.luminis.workshop.smallsteps.api.helper.setupLegoTestApp
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.IncompleteReturn
import eu.luminis.workshop.smallsteps.logic.domainService.state.LegoBox
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LegoStockReportRoutesTest {
    private val millenniumFalcon = LegoSetNumber(75192)
    private val atAt = LegoSetNumber(75288)
    private val r2d2 = LegoSetNumber(75308)

    @Test
    fun `should return missing parts report`() = testApplication {
        // given
        val stockRepository = TestLegoStockRepository(
            StockState(
                incompleteStock = listOf(
                    LegoBox(millenniumFalcon, 42, mapOf("3022" to 7, "20105" to 1)),
                    LegoBox(millenniumFalcon, 37, mapOf("3022" to 3, "60581" to 1)),
                    LegoBox(atAt, 5, mapOf("3022" to 2, "18674" to 1)),
                    LegoBox(r2d2, 8, mapOf("3666" to 2, "64799" to 1)),
                )
            )
        )
        val client = setupLegoTestApp(stockRepository)

        client.get("/stock/report/current-missing-parts") {
            header("X-API-GATEWAY-AUTH", "${TestUsers.bussum}")
            header("X-SELECTED-LEGO-STORE", "${TestUsers.bussum}")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)

            val response = body<List<Pair<String, Int>>>()

            assertEquals(
                listOf(
                    "3022" to 12,
                    "3666" to 2,
                    "18674" to 1,
                    "20105" to 1,
                    "60581" to 1,
                    "64799" to 1
                ),
                response
            )
        }
    }

    @Test
    fun `should return historic most lost parts report`() = testApplication {
        // given
        val stockRepository = TestLegoStockRepository(
            StockState(
                incompleteReturnHistory = listOf(
                    IncompleteReturn(millenniumFalcon, mapOf("3022" to 5, "20105" to 1)),
                    IncompleteReturn(millenniumFalcon, mapOf("3022" to 3, "60581" to 1)),
                    IncompleteReturn(atAt, mapOf("3022" to 2, "18674" to 1)),
                    IncompleteReturn(r2d2, mapOf("3666" to 2, "64799" to 1)),
                )
            )
        )
        val client = setupLegoTestApp(stockRepository)

        client.get("/stock/report/historicly-most-lost") {
            header("X-API-GATEWAY-AUTH", "${TestUsers.bussum}")
            header("X-SELECTED-LEGO-STORE", "${TestUsers.bussum}")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)

            val response = body<List<Pair<String, Int>>>()

            assertEquals(
                listOf(
                    "3022" to 10,
                    "3666" to 2,
                    "18674" to 1,
                    "20105" to 1,
                    "60581" to 1,
                    "64799" to 1
                ),
                response
            )
        }
    }
}