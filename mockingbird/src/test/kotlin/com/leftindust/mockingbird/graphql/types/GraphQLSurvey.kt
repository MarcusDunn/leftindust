package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.survey.SurveyDto
import com.leftindust.mockingbird.util.EntityStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class GraphQLSurvey {
    @Test
    internal fun `create GraphQLFormTemplate from Form Entity`() {
        val entityForm = EntityStore.form("FormTemplateTest.create GraphQLFormTemplate from Form Entity")
            .apply { id = UUID.nameUUIDFromBytes("eruyfq".toByteArray()) }
        val gqlFormTemplate = SurveyDto(entityForm)
        assertEquals(entityForm.name, gqlFormTemplate.name)
    }
}