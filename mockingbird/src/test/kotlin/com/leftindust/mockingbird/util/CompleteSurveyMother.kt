package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntity
import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntityToCompleteSurvey
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionEntityToCompleteSurveySectionConverter
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveyDtoToCreateCompleteSurveyConverter
import com.leftindust.mockingbird.util.CompleteSurveySectionMother.CompleteHowMuchPainAreYouInSection
import com.leftindust.mockingbird.util.SurveyLinkMother.KoosKneeSurveyLink
import java.util.UUID

object CompleteSurveyMother {
    private val completeSurveySectionInputEntityToCompleteSurveySectionInputConverter =
        CompleteSurveySectionInputEntityToCompleteSurveySectionInputConverter()
    private val completeSurveySectionEntityToCompleteSurveySectionConverter =
        CompleteSurveySectionEntityToCompleteSurveySectionConverter(
            completeSurveySectionInputEntityToCompleteSurveySectionInputConverter
        )
    val completeSurveyEntityToCompleteSurvey = CompleteSurveyEntityToCompleteSurvey(
        completeSurveySectionEntityToCompleteSurveySectionConverter
    )

    object FilledOutKoosKneeSurvey {
        val id = UUID.fromString("d30e7ae9-01bc-4027-9825-94709e55b2cd")
        val graphqlId = CompleteSurveyDto.CompleteSurveyDtoId(id)
        val completeSurveyTemplateSections = listOf(CompleteHowMuchPainAreYouInSection.dto)
        val createCompleteSurveyTemplateSectionDtos = listOf(CompleteHowMuchPainAreYouInSection.createDto)
        val createCompleteSurveyTemplateSections = listOf(CompleteHowMuchPainAreYouInSection.create)
        val surveyLinkId = KoosKneeSurveyLink.graphqlId

        val entityPersisted = CompleteSurveyEntity(
            sections = setOf(CompleteHowMuchPainAreYouInSection.entityPersisted),
            surveyLink = KoosKneeSurveyLink.entityDetached
        )
            .apply { id = this@FilledOutKoosKneeSurvey.id }

        val domain = completeSurveyEntityToCompleteSurvey.convert(entityPersisted)

        val createDomain = CreateCompleteSurveyDtoToCreateCompleteSurveyConverter.CreateCompleteSurveyImpl(
            surveyLinkId = surveyLinkId,
            completeSurveyTemplateSections = createCompleteSurveyTemplateSections
        )

        val dto = CompleteSurveyDto(
            id = graphqlId,
            sections = completeSurveyTemplateSections
        )

        val createDto = CreateCompleteSurveyDto(
            surveyLinkId = surveyLinkId,
            completeSurveyTemplateSections = createCompleteSurveyTemplateSectionDtos
        )
    }
}