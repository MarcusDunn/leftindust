package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.form.GraphQLFormTemplate
import com.leftindust.mockingbird.util.EntityStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class GraphQLFormTemplateTest {
    @Test
    internal fun `create GraphQLFormTemplate from Form Entity`() {
        val entityForm = EntityStore.form("FormTemplateTest.create GraphQLFormTemplate from Form Entity")
            .apply { id = UUID.nameUUIDFromBytes("eruyfq".toByteArray()) }
        val gqlFormTemplate = GraphQLFormTemplate(entityForm)
        assertEquals(entityForm.name, gqlFormTemplate.name)
    }
}