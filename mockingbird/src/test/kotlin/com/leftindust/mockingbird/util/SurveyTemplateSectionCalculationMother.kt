package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.*
import dev.forkhandles.result4k.onFailure
import java.util.*

object SurveyTemplateSectionCalculationMother {
    val surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter =
        SurveyTemplateCalculationToSurveyTemplateCalculationDtoConverter()

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


        val domain = entityDetached.toSurveyTemplateCalculation().onFailure { throw it.reason.toMockingbirdException() }

        val dto = surveyTemplateCalculationToSurveyTemplateCalculationDtoConverter.convert(domain)
    }
}