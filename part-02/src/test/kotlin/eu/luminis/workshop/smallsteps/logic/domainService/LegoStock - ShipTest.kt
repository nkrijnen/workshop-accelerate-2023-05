package eu.luminis.workshop.smallsteps.logic.domainService

import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper.millenniumFalcon
import eu.luminis.workshop.smallsteps.logic.domainService.auth.AccessDeniedException
import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@Suppress("ClassName")
internal class `LegoStock - ShipTest` {
    @Test
    fun `should register shipping lego set when reserved`() {
        // given
        val initialState = StockState(
            reserved = setOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(initialState, auth = TestUsers.bussumAuth)

        // when
        val newState = handler.ship(millenniumFalcon, TestUsers.harry)

        // then
        assertEquals(emptySet(), newState.reserved)
        assertEquals(listOf(millenniumFalcon to TestUsers.harry), newState.atBuilder)
        assertEquals(initialState.availableStock, newState.availableStock)
        assertEquals(initialState.incompleteStock, newState.incompleteStock)
        assertEquals(initialState.incompleteReturnHistory, newState.incompleteReturnHistory)
    }

    @Test
    fun `should fail to ship when not reserved by builder`() {
        // given
        val initialState = StockState(
            reserved = emptySet(),
        )
        val handler = LegoStockHelper.build(initialState, auth = TestUsers.bussumAuth)

        // then
        assertFailsWith<IllegalStateException> {
            // when
            handler.ship(millenniumFalcon, TestUsers.harry)
        }
    }

    @Test
    fun `should fail to ship when not authenticated as shop`() {
        // given
        val initialState = StockState(
            reserved = setOf(
                millenniumFalcon to TestUsers.harry
            ),
        )
        val handler = LegoStockHelper.build(initialState, auth = TestUsers.harryAuth)

        // then
        assertFailsWith<AccessDeniedException> {
            // when
            handler.ship(millenniumFalcon, TestUsers.harry)
        }
    }
}