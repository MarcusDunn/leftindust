package com.leftindust.mockingbird.extensions

import com.expediagroup.graphql.generator.scalars.ID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EventDtoIdKtTest {

    @Test
    fun `gqlID with ints`() {
        assertEquals(ID("10"), gqlID(10))
    }

    @Test
    fun `testGqlID with longs`() {
        assertEquals(ID("10"), gqlID(10L))
    }
}