package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateCalculationDto
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateCalculationEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateCalculationEntityToSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateCalculationToSurveyTemplateCalculationDtoConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateInputType
import java.util.UUID

object SurveySectionTemplateCalculationMother {
    val createSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter = CreateSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter()
    val surveyTemplateCalculationEntityToSurveyTemplateCalculationConverter = SurveyTemplateCalculationEntityToSurveyTemplateCalculationConverter()
    val surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter = SurveyTemplateCalculationToSurveyTemplateCalculationDtoConverter()

    object FirstCalculation {
        val id = UUID.fromString("abe1b9b4-fef9-4545-bf91-b8c9b37c14c7")
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
        ).apply { id = this@FirstCalculation.id }

        val domain = surveyTemplateCalculationEntityToSurveyTemplateCalculationConverter.convert(entityPersisted)

        val dto = surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter.convert(domain)
    }
}