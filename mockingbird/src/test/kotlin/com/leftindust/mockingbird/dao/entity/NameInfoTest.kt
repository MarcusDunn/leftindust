package com.leftindust.mockingbird.dao.entity

import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLNameInfoEditInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class NameInfoTest {

    @Test
    fun setByGqlInput() {
        val original = NameInfo(
            firstName = "hello",
            middleName = "flat",
            lastName = "world"
        )

        original.setByGqlInput(
            GraphQLNameInfoEditInput(
                firstName = null,
                middleName = OptionalInput.Undefined,
                lastName = "earth"
            )
        )

        val expected = NameInfo(
            firstName = "hello",
            middleName = "flat",
            lastName = "earth"
        )

        assertEquals(expected.firstName, original.firstName)
        assertEquals(expected.middleName, original.middleName)
        assertEquals(expected.lastName, original.lastName)
    }
}