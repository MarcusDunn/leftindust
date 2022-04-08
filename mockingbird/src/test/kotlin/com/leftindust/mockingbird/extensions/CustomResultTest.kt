package com.leftindust.mockingbird.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CustomResultTest {

    @Test
    fun isSuccess() {
        val customResult: CustomResult<Int, *> = Success(10)
        assertEquals(true, customResult.isSuccess())
        assertEquals(false, customResult.isFailure())
    }

    @Test
    fun isFailure() {
        val customResult: CustomResult<*, Int> = Failure(10)
        assertEquals(true, customResult.isFailure())
        assertEquals(false, customResult.isSuccess())
    }

    @Test
    fun `getOrNull returns null on failure`() {
        val customResult: CustomResult<*, Int> = Failure(10)
        assertEquals(null, customResult.getOrNull())
    }

    @Test
    fun `getOrNull returns value on success`() {
        val customResult: CustomResult<Int, *> = Success(10)
        assertEquals(10, customResult.getOrNull())
    }
}