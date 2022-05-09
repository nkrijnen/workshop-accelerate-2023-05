package eu.luminis.workshop.smallsteps.logic.domainService

import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper.millenniumFalcon
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AccessDeniedException
import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.LegoBox
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@Suppress("ClassName")
internal class `LegoStock - ReturningTest` {
    private val missingParts = mapOf(
        "3898b" to 1,
        "818622" to 3,
    )

    @Test
    fun `should register returning complete lego set`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(initialState)

        // when
        val newState = handler.returningComplete(millenniumFalcon)

        // then
        assertEquals(initialState, newState)
    }

    @Test
    fun `should fail to register returning lego set when not at builder`() {
        // given
        val initialState = StockState(
            atBuilder = emptyList(),
        )
        val handler = LegoStockHelper.build(initialState)

        // then
        assertFailsWith<IllegalStateException> {
            // when
            handler.returningComplete(millenniumFalcon)
        }

        // and
        assertFailsWith<IllegalStateException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, missingParts))
        }
    }

    @Test
    fun `should fail to register returning lego set when not at builder but is at different builder`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.sally
            ),
        )
        val handler = LegoStockHelper.build(initialState)

        // then
        assertFailsWith<IllegalStateException> {
            // when
            handler.returningComplete(millenniumFalcon)
        }

        // and
        assertFailsWith<IllegalStateException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, missingParts))
        }
    }

    @Test
    fun `should fail to register returning lego set when not authenticated as builder`() {
        // given
        val initialState = StockState(
            atBuilder = listOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(initialState, auth = TestUsers.bussumAuth)

        // then
        assertFailsWith<AccessDeniedException> {
            // when
            handler.returningComplete(millenniumFalcon)
        }

        // and
        assertFailsWith<AccessDeniedException> {
            // when
            handler.returningInComplete(LegoBox(millenniumFalcon, 42, missingParts))
        }
    }
}