package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputData
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputEntity
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyInput
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyInputDto
import com.leftindust.mockingbird.survey.complete.SurveyInputType
import java.util.UUID

object CompleteSurveySectionInputMother {
    private val completeSurveySectionInputEntityToCompleteSurveySectionInputConverter = CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter()
    private val completeSurveySectionInputToCompleteSurveySectionInputDtoConverter = CompleteSurveySectionInputToCompleteSurveySectionInputDtoConverter()
    object FilledOutHowBadIsThePainWhenIPokeIt {
        val id = UUID.fromString("6f88c023-4479-43b1-bc42-6018e48da7e5")
        val value = "like prob like a 10 maybe"
        val surveyTemplateSectionInputId = SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput.graphqlId
        val entityTransient = CompleteSurveySectionInputEntity(value = CompleteSurveySectionInputData.StringValue(value))
            .apply { id = this@FilledOutHowBadIsThePainWhenIPokeIt.id }
        val domain = completeSurveySectionInputEntityToCompleteSurveySectionInputConverter.convert(entityTransient)
        val dto = completeSurveySectionInputToCompleteSurveySectionInputDtoConverter.convert(domain)


        val createDto = CreateCompleteSurveyInputDto(
            surveyTemplateSectionInputId = surveyTemplateSectionInputId,
            type = SurveyInputType.String,
            stringInput = value,
            numberInput = null,
            stringArrayInput = null,
            numberArrayInput = null
        )

        val create = object : CreateCompleteSurveyInput {
            override val surveyTemplateSectionInputId = this@FilledOutHowBadIsThePainWhenIPokeIt.surveyTemplateSectionInputId
            override val value = CompleteSurveySectionInputData.StringValue(this@FilledOutHowBadIsThePainWhenIPokeIt.value)
        }
    }
}