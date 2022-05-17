package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.survey.SurveySectionDto
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GraphQLSurveySectionTestTemplate {
    @Test
    internal fun `create GraphQLFormSection from FormSection Entity`() {
        val section = EntityStore.form("GraphQLFormSectionTest.create GraphQLFormSection from FormSection Entity").sections.first()
        val gqlSection = SurveySectionDto(section.apply { id = makeUUID() })
        assertEquals(section.number, gqlSection.number)
    }
}