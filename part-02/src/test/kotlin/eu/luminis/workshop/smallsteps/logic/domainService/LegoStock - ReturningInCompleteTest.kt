package eu.luminis.workshop.smallsteps.logic.domainService

import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper
import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper.millenniumFalcon
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.IncompleteReturn
import eu.luminis.workshop.smallsteps.logic.domainService.state.LegoBox
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@Suppress("ClassName")
internal class `LegoStock - ReturningInCompleteTest` {
    private val partCatalog = mapOf(
        // https://www.bricklink.com/catalogItemInv.asp?S=75192-1
        millenniumFalcon to mapOf(
            "3022" to 8, // Black Plate 2 x 2
            "2420" to 2, // Black Plate 2 x 2 Corner
            "60581" to 2, // Black Panel 1 x 4 x 3 with Side Supports - Hollow Studs
            "4865b" to 2, // Black Panel 1 x 2 x 1 with Rounded Corners
            "20105" to 1 // Black Minifigure, Weapon Crossbow with Mini Blaster / Shooter
        )
    )

    private val missingPartsForMilleniumFalcon = mapOf(
        "20105" to 1,
        "3022" to 3,
    )

    private val nonMilleniumFalconParts = mapOf(
        "00000" to 1,
        "99999c" to 2,
    )

    @Test
    fun `should register returning incomplete lego set`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(
            initialState,
            partCatalog = partCatalog,
        )

        // when
        val newState = handler.returningInComplete(LegoBox(millenniumFalcon, 42, missingPartsForMilleniumFalcon))

        // then
        assertEquals(listOf(LegoBox(millenniumFalcon, 42, missingPartsForMilleniumFalcon)), newState.incompleteStock)
        assertEquals(
            listOf(IncompleteReturn(millenniumFalcon, missingPartsForMilleniumFalcon)),
            newState.incompleteReturnHistory
        )
        assertEquals(initialState.atBuilder, newState.atBuilder)
        assertEquals(initialState.availableStock, newState.availableStock)
        assertEquals(initialState.reserved, newState.reserved)
    }

    @Test
    fun `should fail to register returning incomplete lego set when no missing parts specified`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(
            initialState,
            partCatalog = partCatalog,
        )

        // then
        assertFailsWith<IllegalArgumentException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, emptyMap()))
        }
    }

    @Test
    fun `should fail to register returning incomplete lego set when missing parts specified that don't belong to the lego set`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(
            initialState,
            partCatalog = partCatalog,
        )

        // then
        assertFailsWith<IllegalArgumentException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, nonMilleniumFalconParts))
        }

        // and
        assertFailsWith<IllegalArgumentException> {
            // when
            handler.returningInComplete(
                LegoBox(
                    millenniumFalcon,
                    42,
                    nonMilleniumFalconParts + nonMilleniumFalconParts
                )
            )
        }
    }

    @Test
    fun `should fail to register returning incomplete lego set when more missing parts are reported than what was in the original set`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(
            initialState,
            partCatalog = partCatalog,
        )

        // then
        assertFailsWith<IllegalArgumentException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, mapOf("20105" to 5))) // only 1 part of set
        }

        // and
        assertFailsWith<IllegalArgumentException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, mapOf("20105" to 0)))
        }
    }

    // count total missing parts

}