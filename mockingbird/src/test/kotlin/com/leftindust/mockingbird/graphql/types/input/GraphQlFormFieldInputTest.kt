package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.form.GraphQlFormFieldInput
import com.leftindust.mockingbird.form.DataType
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GraphQlFormFieldInputTest {
    @Test
    internal fun `check can create a multiselect form with possibilities`() {
        assertDoesNotThrow {
            GraphQlFormFieldInput(
                title = "my form feild",
                dataType = DataType.MultiMultiSelect,
                number = 1,
                multiSelectPossibilities = listOf("my one possibility")
            )
        }
    }

    @Test
    internal fun `check can create a non-multiselect form with no possibilities`() {
        assertDoesNotThrow {
            GraphQlFormFieldInput(
                title = "my form feild",
                dataType = DataType.Float,
                number = 1,
                multiSelectPossibilities = emptyList()
            )
        }
    }

    @Test
    internal fun `check cannot create a multiselect form with no possibilities`() {
        assertThrows<IllegalArgumentException> {
            GraphQlFormFieldInput(
                title = "my form feild",
                dataType = DataType.SingleMultiSelect,
                number = 1,
                multiSelectPossibilities = emptyList()
            )
        }
    }
}