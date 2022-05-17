package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.survey.SurveyFieldDto
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.makeUUID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SurveyFieldDtoTestEntityTemplate {
    @Test
    internal fun `create GraphQLFormFieldTest from FormField Entity`() {
        val formField = EntityStore.formField("GraphQlFormFieldTest.create GraphQLFormFieldTest from FormField Entity")
        val gqlFormField = SurveyFieldDto(formField.apply { id = makeUUID() })
        assertEquals(formField.surveyFieldType, gqlFormField.surveyFieldType)
        assertEquals(formField.number, gqlFormField.number)
    }
}