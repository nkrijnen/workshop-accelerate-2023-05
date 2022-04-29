package eu.luminis.workshop.smallsteps

import kotlin.test.Test
import kotlin.test.assertEquals

class HelloTest {
    @Test
    fun `should say hello`() {
        assertEquals("Hello kotlin", hello("kotlin"))
    }
}