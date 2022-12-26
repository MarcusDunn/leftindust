package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateCalculationDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateCalculationEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateCalculationEntityToSurveyTemplateCalculationConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateCalculationToSurveyTemplateCalculationDtoConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateInputType
import java.util.UUID

object SurveyTemplateSectionCalculationMother {
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

        val entityDetached = SurveyTemplateCalculationEntity(
            index = 0,
            label = label,
            inputType = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        ).apply { id = this@FirstCalculation.id }

        val entityTransient = SurveyTemplateCalculationEntity(
            index = 0,
            label = label,
            inputType = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        )


        val domain = surveyTemplateCalculationEntityToSurveyTemplateCalculationConverter.convert(entityDetached)

        val dto = surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter.convert(domain)
    }
}