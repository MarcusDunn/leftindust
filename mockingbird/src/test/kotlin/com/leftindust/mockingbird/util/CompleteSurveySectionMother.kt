package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionEntity
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionEntityToCompleteSurveySectionConverter
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionToCompleteSurveySectionDtoConverter
import com.leftindust.mockingbird.survey.complete.CreateCompleteSurveySectionDto
import com.leftindust.mockingbird.util.CompleteSurveyInputMother.CompleteRateThePain
import com.leftindust.mockingbird.util.CompleteSurveySectionInputMother.FilledOutHowBadIsThePainWhenIPokeIt
import com.leftindust.mockingbird.util.SurveyTemplateSectionMother.HowMuchPainAreYouInSection
import java.util.UUID

object CompleteSurveySectionMother {
    val completeSurveySectionToCompleteSurveySectionDtoConverter = CompleteSurveySectionToCompleteSurveySectionDtoConverter()
    val completeSurveySectionEntityToCompleteSurveySectionConverter = CompleteSurveySectionEntityToCompleteSurveySectionConverter()

    object CompleteHowMuchPainAreYouInSection {
        val id = UUID.fromString("4ca0f6c1-b2a9-4751-a483-95e87befcbcb")
        val graphqlId = CompleteSurveySectionDto.CompleteSurveySectionDtoId(id)
        val entityPersisted = CompleteSurveySectionEntity(
            inputs = setOf(FilledOutHowBadIsThePainWhenIPokeIt.entityPersisted)
        )
            .apply { id = this@CompleteHowMuchPainAreYouInSection.id }
        val domain = completeSurveySectionEntityToCompleteSurveySectionConverter.convert(entityPersisted)
        val dto =  completeSurveySectionToCompleteSurveySectionDtoConverter.convert(domain)
        val completedSurveyInputs = listOf(CompleteRateThePain.createDto)
        val surveyTemplateSectionId = HowMuchPainAreYouInSection.graphqlId
        val createDto = CreateCompleteSurveySectionDto(
            surveyTemplateSectionId = surveyTemplateSectionId,
            completedSurveyInputs = completedSurveyInputs
        )
    }
}