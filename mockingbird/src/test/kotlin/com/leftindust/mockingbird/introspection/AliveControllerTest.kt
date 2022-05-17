package com.leftindust.mockingbird.introspection

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AliveControllerTest {
    @Test
    internal fun `check is alive returns true`() {
        val aliveController = AliveController()
        assertEquals(true, aliveController.isAlive())
    }
}