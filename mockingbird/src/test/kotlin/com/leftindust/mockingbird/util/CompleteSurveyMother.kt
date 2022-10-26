package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntity
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntityToCompleteSurvey
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurvey
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyDto
import com.leftindust.mockingbird.util.CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection
import com.leftindust.mockingbird.util.SurveyLinkMother.KoosKneeSurveyLink
import java.util.UUID

object CompleteSurveyMother {

    val completeSurveyEntityToCompleteSurvey = CompleteSurveyEntityToCompleteSurvey()

    object FilledOutKoosKneeSurvey {
        val id = UUID.fromString("d30e7ae9-01bc-4027-9825-94709e55b2cd")
        val graphqlId = CompleteSurveyDto.CompleteSurveyDtoId(id)
        val completeSurveyTemplateSections = listOf(CompleteHowMuchPainAreYouInSection.dto)
        val createCompleteSurveyTemplateSectionDtos = listOf(CompleteHowMuchPainAreYouInSection.createDto)
        val createCompleteSurveyTemplateSections = listOf(CompleteHowMuchPainAreYouInSection.create)
        val surveyLinkId = KoosKneeSurveyLink.graphqlId

        val entityDetached = CompleteSurveyEntity(
            sections = setOf(CompleteHowMuchPainAreYouInSection.entityPersisted),
            surveyLink = KoosKneeSurveyLink.entityDetached
        ).apply { id = this@FilledOutKoosKneeSurvey.id }

        val entityTransient = CompleteSurveyEntity(
            sections = setOf(CompleteHowMuchPainAreYouInSection.entityPersisted),
            surveyLink = KoosKneeSurveyLink.entityDetached
        )

        val domain = completeSurveyEntityToCompleteSurvey.convert(entityDetached)

        val createDomain = object : CreateCompleteSurvey {
            override val surveyLinkId = this@FilledOutKoosKneeSurvey.surveyLinkId
            override val completeSurveyTemplateSections = this@FilledOutKoosKneeSurvey.createCompleteSurveyTemplateSections
        }

        val dto = CompleteSurveyDto(
            id = graphqlId
        )

        val createDto = CreateCompleteSurveyDto(
            surveyLinkId = surveyLinkId,
            completeSurveyTemplateSections = createCompleteSurveyTemplateSectionDtos
        )
    }
}