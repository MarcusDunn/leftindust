package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveySectionDto
import com.leftindust.mockingbird.util.CompleteSurveyInputMother.CompleteRateThePain
import com.leftindust.mockingbird.util.SurveyTemplateSectionMother.HowMuchPainAreYouInSection

object CompleteSurveySectionMother {
    object CompleteHowMuchPainAreYouInSection {
        val completedSurveyInputs = listOf(CompleteRateThePain.createDto)
        val surveyTemplateSectionId = HowMuchPainAreYouInSection.graphqlId
        val createDto = CreateCompleteSurveySectionDto(
            surveyTemplateSectionId = surveyTemplateSectionId,
            completedSurveyInputs = completedSurveyInputs
        )
    }
}