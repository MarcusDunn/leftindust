package com.leftindust.mockingbird.extensions

import com.expediagroup.graphql.generator.execution.OptionalInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class OptionalInputKtTest {

    @Test
    fun `getOrDefault with defined value`() {
        val optionalInput = OptionalInput.Defined(10)

        assertEquals(10, optionalInput.getOrDefault(20))
    }

    @Test
    fun `getOrDefault with undefined value`() {
        val optionalInput = OptionalInput.Undefined

        assertEquals(20, optionalInput.getOrDefault(20))
    }

    @Test
    fun `getOrDefault with null value`() {
        val optionalInput = OptionalInput.Defined(null)

        assertEquals(20, optionalInput.getOrDefault(20))
    }

    @Test
    fun `getOrThrow with defined value`() {
        val optionalInput = OptionalInput.Defined(10)

        assertEquals(10, optionalInput.getOrThrow())

    }

    @Test
    fun `getOrThrow with undefined value`() {
        val optionalInput = OptionalInput.Undefined

        assertThrows<Throwable> {
            optionalInput.getOrThrow()
        }
    }

    @Test
    fun `getOrThrow with null value`() {
        val optionalInput = OptionalInput.Defined(null)

        assertThrows<Throwable> {
            optionalInput.getOrThrow()
        }
    }

    @Test
    fun `getOrNull with defined value`() {
        val optionalInput = OptionalInput.Defined(10)

        assertEquals(10, optionalInput.getOrNull())
    }

    @Test
    fun `getOrNull with undefined value`() {
        val optionalInput: OptionalInput<Int> = OptionalInput.Undefined

        assertEquals(null, optionalInput.getOrNull())
    }

    @Test
    fun `getOrNull with null value`() {
        val optionalInput: OptionalInput<Int> = OptionalInput.Defined(null)

        assertEquals(null, optionalInput.getOrNull())
    }

    @Test
    fun `onUndefined with defined value`() {
        val optionalInput = OptionalInput.Defined(10)

        assertEquals(10, optionalInput.onUndefined(20))

    }

    @Test
    fun `onUndefined with undefined value`() {
        val optionalInput = OptionalInput.Undefined

        assertEquals(20, optionalInput.onUndefined(20))
    }

    @Test
    fun `onUndefined with null value`() {
        val optionalInput = OptionalInput.Defined(null)

        assertEquals(null, optionalInput.onUndefined(10))

    }
}