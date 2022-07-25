package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.CreateSurveyTemplateCalculationDto
import com.leftindust.mockingbird.survey.CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.SurveyTemplateCalculationEntity
import com.leftindust.mockingbird.survey.SurveyTemplateInputType
import java.util.UUID

object SurveySectionTemplateCalculationMother {
    val createSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter = CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter()

    object FirstCalculation {
        val label = "birthday"
        val inputType = SurveyTemplateInputType.Date
        val showOnComplete = true
        val calculation = """rand()"""

        val createDto = CreateSurveyTemplateCalculationDto(
            label = label,
            inputType = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        )

        val entityPersisted = SurveyTemplateCalculationEntity(
            index = 0,
            label = label,
            inputType = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        ).apply { id = UUID.fromString("abe1b9b4-fef9-4545-bf91-b8c9b37c14c7") }
    }
}