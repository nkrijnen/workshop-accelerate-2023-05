package eu.luminis.workshop.smallsteps.logic.domainService

import eu.luminis.workshop.smallsteps.logic.domainService.auth.AccessDeniedException
import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper
import eu.luminis.workshop.smallsteps.logic.domainService.helper.LegoStockHelper.millenniumFalcon
import eu.luminis.workshop.smallsteps.logic.domainService.helper.TestUsers
import eu.luminis.workshop.smallsteps.logic.domainService.state.StockState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@Suppress("ClassName")
internal class `LegoStock - ReserveTest` {
    @Test
    fun `should reserve lego set when in stock`() {
        // given
        val initialState = StockState(
            availableStock = mapOf(millenniumFalcon to 1),
            reserved = emptySet(),
        )
        val legoStock = LegoStockHelper.build(initialState)

        // when
        val newState = legoStock.reserve(millenniumFalcon)

        // then
        assertEquals(mapOf(millenniumFalcon to 0), newState.availableStock)
        assertEquals(setOf(millenniumFalcon to TestUsers.harry), newState.reserved)
        assertEquals(initialState.atBuilder, newState.atBuilder)
        assertEquals(initialState.incompleteStock, newState.incompleteStock)
        assertEquals(initialState.incompleteReturnHistory, newState.incompleteReturnHistory)
    }

    @Test
    fun `should fail to reserve when lego set not in store`() {
        // given
        val legoStock = LegoStockHelper.build(StockState())

        // then
        assertFailsWith<IllegalArgumentException> {
            // when
            legoStock.reserve(millenniumFalcon)
        }
    }

    @Test
    fun `should fail to reserve when lego set 0 in store`() {
        // given
        val initialState = StockState(
            availableStock = mapOf(millenniumFalcon to 0)
        )
        val legoStock = LegoStockHelper.build(initialState)

        // then
        assertFailsWith<IllegalArgumentException> {
            // when
            legoStock.reserve(millenniumFalcon)
        }
    }

    @Test
    fun `should fail to reserve when not authenticated as builder`() {
        // given
        val initialState = StockState(
            availableStock = mapOf(millenniumFalcon to 1),
            reserved = emptySet(),
        )
        val legoStock = LegoStockHelper.build(initialState, auth = TestUsers.bussumAuth)

        // then
        assertFailsWith<AccessDeniedException> {
            // when
            legoStock.reserve(millenniumFalcon)
        }
    }
}