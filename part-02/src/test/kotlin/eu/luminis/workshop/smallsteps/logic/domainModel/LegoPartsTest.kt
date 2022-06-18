package eu.luminis.workshop.smallsteps.logic.domainModel

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LegoPartsTest {
    @Test
    fun isNotEmpty() {
        assertFalse(LegoParts().isNotEmpty())
        assertTrue(LegoParts(mapOf("23894" to 3)).isNotEmpty())
    }

    @Test
    fun hasNoneWithCountZero() {
        assertTrue(LegoParts().hasNoneWithCountZero())
        assertTrue(LegoParts(mapOf("1244" to 1)).hasNoneWithCountZero())
        assertFalse(LegoParts(mapOf("1244" to 0)).hasNoneWithCountZero())
        assertFalse(LegoParts(mapOf("1244" to 0, "2355" to 5)).hasNoneWithCountZero())
        assertTrue(LegoParts(mapOf("1244" to 8, "2355" to 5)).hasNoneWithCountZero())
    }

    @Test
    fun partsPresentIn() {
        assertTrue(LegoParts().partsPresentIn(LegoParts()))
        assertTrue(LegoParts(mapOf("1234" to 1)).partsPresentIn(LegoParts(mapOf("1234" to 1))))
        assertTrue(LegoParts(mapOf("1234" to 1)).partsPresentIn(LegoParts(mapOf("1234" to 1, "8757" to 2))))
        assertTrue(LegoParts(mapOf("1234" to 5, "8757" to 1)).partsPresentIn(LegoParts(mapOf("1234" to 0, "8757" to 0))))
        assertFalse(LegoParts(mapOf("1234" to 5, "8757" to 1)).partsPresentIn(LegoParts()))
        assertFalse(LegoParts(mapOf("1234" to 1)).partsPresentIn(LegoParts(mapOf("4321" to 1, "8757" to 2))))
    }

    @Test
    fun hasNoMoreThan() {
    }

    @Test
    fun plus() {
    }
}