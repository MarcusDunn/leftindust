package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.survey.CreateSurveyFieldDto
import com.leftindust.mockingbird.survey.SurveyFieldType
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SurveyFieldDtoInputTestEntityTemplate {
    @Test
    internal fun `check can create a multiselect form with possibilities`() {
        assertDoesNotThrow {
            CreateSurveyFieldDto(
                title = "my form feild",
                surveyFieldType = SurveyFieldType.MultiMultiSelect,
                number = 1,
                multiSelectPossibilities = listOf("my one possibility")
            )
        }
    }

    @Test
    internal fun `check can create a non-multiselect form with no possibilities`() {
        assertDoesNotThrow {
            CreateSurveyFieldDto(
                title = "my form feild",
                surveyFieldType = SurveyFieldType.Float,
                number = 1,
                multiSelectPossibilities = emptyList()
            )
        }
    }

    @Test
    internal fun `check cannot create a multiselect form with no possibilities`() {
        assertThrows<IllegalArgumentException> {
            CreateSurveyFieldDto(
                title = "my form feild",
                surveyFieldType = SurveyFieldType.SingleMultiSelect,
                number = 1,
                multiSelectPossibilities = emptyList()
            )
        }
    }
}