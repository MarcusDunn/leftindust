package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntity
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntityToCompleteSurvey
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyDto
import com.leftindust.mockingbird.util.CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection
import com.leftindust.mockingbird.util.SurveyTemplateMother.KoosKneeSurvey
import java.util.UUID

object CompleteSurveyMother {
    val completeSurveyEntityToCompleteSurvey = CompleteSurveyEntityToCompleteSurvey()

    object FilledOutKoosKneeSurvey {
        val id = UUID.fromString("d30e7ae9-01bc-4027-9825-94709e55b2cd")
        val graphqlId = CompleteSurveyDto.CompleteSurveyDtoId(id)
        val completeSurveyTemplateSections = listOf(CompleteHowMuchPainAreYouInSection.createDto)
        val surveyTemplateId = KoosKneeSurvey.graphqlId

        val entityPersisted = CompleteSurveyEntity(
            sections = setOf(CompleteHowMuchPainAreYouInSection.entityPersisted),
            surveyTemplate = KoosKneeSurvey.entityPersisted
        )
            .apply { id = this@FilledOutKoosKneeSurvey.id }

        val domain = completeSurveyEntityToCompleteSurvey.convert(entityPersisted)

        val dto = CompleteSurveyDto(
            id = graphqlId
        )

        val createDto = CreateCompleteSurveyDto(
            surveyTemplateId = surveyTemplateId,
            completeSurveyTemplateSections = completeSurveyTemplateSections
        )
    }
}