package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyDtoToCreateCompleteSurveyConverter
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyInput
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyInputDto
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveySection
import com.leftindust.mockingbird.survey.complete.JsonData
import com.leftindust.mockingbird.survey.complete.SurveyInputType
import com.leftindust.mockingbird.util.SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput

object CompleteSurveyInputMother {
    object CompleteRateThePain {
        val surveyTemplateSectionInputId = HowMuchPainAreYouInSectionInput.graphqlId
        val createDto = CreateCompleteSurveyInputDto(
            surveyTemplateSectionInputId = surveyTemplateSectionInputId,
            type = SurveyInputType.String,
            stringInput = "a lot",
            numberInput = null,
            stringArrayInput = null,
            numberArrayInput = null
        )

        val create = CreateCompleteSurveyDtoToCreateCompleteSurveyConverter.CreateCompleteSurveyInputImpl(
            surveyTemplateSectionInputId = surveyTemplateSectionInputId,
            value = JsonData.StringValue("a lot")
        )
    }
}