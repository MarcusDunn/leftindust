package com.leftindust.mockingbird.extensions

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConcurrentFlowsKtTest {

    @Test
    fun parallelMap() {
        runBlocking {
            val someList = 0 until 20

            val result = someList.parallelMap { it * 2 }

            val expected = someList.map { it * 2 }

            assertEquals(expected, result)
        }
    }
}