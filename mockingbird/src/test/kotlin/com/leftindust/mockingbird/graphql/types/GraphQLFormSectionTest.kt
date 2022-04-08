package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GraphQLFormSectionTest {
    @Test
    internal fun `create GraphQLFormSection from FormSection Entity`() {
        val section = EntityStore.form("GraphQLFormSectionTest.create GraphQLFormSection from FormSection Entity").sections.first()
        val gqlSection = GraphQLFormSection(section.apply { id = makeUUID() }, mockk())
        assertEquals(section.number, gqlSection.number)
    }
}