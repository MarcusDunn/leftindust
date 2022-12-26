package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.*
import dev.forkhandles.result4k.onFailure
import java.util.UUID

object CompleteSurveySectionInputMother {
    object FilledOutHowBadIsThePainWhenIPokeIt {
        val id = UUID.fromString("6f88c023-4479-43b1-bc42-6018e48da7e5")
        val value = "like prob like a 10 maybe"
        val surveyTemplateSectionInputId = SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput.graphqlId
        val entityTransient = CompleteSurveySectionInputEntity(value = CompleteSurveySectionInputData.StringValue(value))
            .apply { id = this@FilledOutHowBadIsThePainWhenIPokeIt.id }
        val domain = entityTransient.toCompleteSurveySectionInput().onFailure { throw it.reason.toMockingbirdException() }
        val dto = domain.toCompleteSurveySectionInputDto()


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