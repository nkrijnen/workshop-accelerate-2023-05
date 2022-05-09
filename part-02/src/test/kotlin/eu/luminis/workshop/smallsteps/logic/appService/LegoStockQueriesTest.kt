package eu.luminis.workshop.smallsteps.logic.appService

import eu.luminis.workshop.smallsteps.api.helper.TestLegoStockRepository
import eu.luminis.workshop.smallsteps.logic.domainModel.valueObjects.LegoSetNumber
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.IncompleteReturn
import eu.luminis.workshop.smallsteps.logic.domainService.state.LegoBox
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LegoStockQueriesTest {
    private val millenniumFalcon = LegoSetNumber(75192)
    private val atAt = LegoSetNumber(75288)
    private val r2d2 = LegoSetNumber(75308)

    @Test
    fun `should return report with of all currently missing parts`() {
        // given
        val queries = LegoStockQueries(
            TestLegoStockRepository(
                StockState(
                    incompleteStock = listOf(
                        LegoBox(millenniumFalcon, 42, mapOf("3022" to 7, "20105" to 1)),
                        LegoBox(millenniumFalcon, 37, mapOf("3022" to 3, "60581" to 1)),
                        LegoBox(atAt, 5, mapOf("3022" to 2, "18674" to 1)),
                        LegoBox(r2d2, 8, mapOf("3666" to 2, "64799" to 1)),
                    )
                )
            )
        )

        // when
        val result = queries.currentlyMissingPartsReport(TestUsers.bussum)

        // then
        assertEquals(
            listOf(
                "3022" to 12,
                "3666" to 2,
                "18674" to 1,
                "20105" to 1,
                "60581" to 1,
                "64799" to 1
            ),
            result
        )
    }

    @Test
    fun `should return report of historically most lost parts`() {
        // given
        val queries = LegoStockQueries(
            TestLegoStockRepository(
                StockState(
                    incompleteReturnHistory = listOf(
                        IncompleteReturn(millenniumFalcon, mapOf("3022" to 7, "20105" to 1)),
                        IncompleteReturn(millenniumFalcon, mapOf("3022" to 3, "60581" to 1)),
                        IncompleteReturn(atAt, mapOf("3022" to 2, "18674" to 1)),
                        IncompleteReturn(r2d2, mapOf("3666" to 2, "64799" to 1)),
                    )
                )
            )
        )

        // when
        val result = queries.historicallyMostLostParts(TestUsers.bussum)

        // then
        assertEquals(
            listOf(
                "3022" to 12,
                "3666" to 2,
                "18674" to 1,
                "20105" to 1,
                "60581" to 1,
                "64799" to 1
            ),
            result
        )
    }
}