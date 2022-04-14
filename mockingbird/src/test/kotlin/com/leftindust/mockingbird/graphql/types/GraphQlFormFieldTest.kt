package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GraphQlFormFieldTest {
    @Test
    internal fun `create GraphQLFormFieldTest from FormField Entity`() {
        val formField = EntityStore.formField("GraphQlFormFieldTest.create GraphQLFormFieldTest from FormField Entity")
        val gqlFormField = GraphQlFormField(formField.apply { id = makeUUID() })
        assertEquals(formField.dataType, gqlFormField.dataType)
        assertEquals(formField.number, gqlFormField.number)
    }
}