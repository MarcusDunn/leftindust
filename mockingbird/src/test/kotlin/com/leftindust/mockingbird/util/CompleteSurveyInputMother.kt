package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyInputDto
import com.leftindust.mockingbird.util.SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput

object CompleteSurveyInputMother {
    object CompleteRateThePain {
        val value = "a lot"
        val surveyTemplateSectionInputId = HowMuchPainAreYouInSectionInput.graphqlId
        val calculationId = 0
        val createDto = CreateCompleteSurveyInputDto(
            surveyTemplateSectionInputId = surveyTemplateSectionInputId,
            value = value,
            calculationId = calculationId
        )
    }
}