package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.complete.*
import com.leftindust.mockingbird.util.CompleteSurveySectionInputMother.FilledOutHowBadIsThePainWhenIPokeIt
import com.leftindust.mockingbird.util.SurveyTemplateSectionMother.HowMuchPainAreYouInSection
import dev.forkhandles.result4k.onFailure
import java.util.UUID

object CompleteSurveySectionMother {
    val completeSurveySectionToCompleteSurveySectionDtoConverter = CompleteSurveySectionToCompleteSurveySectionDtoConverter()


    object CompleteHowMuchPainAreYouInSection {
        val id = UUID.fromString("4ca0f6c1-b2a9-4751-a483-95e87befcbcb")
        val graphqlId = CompleteSurveySectionDto.CompleteSurveySectionDtoId(id)
        val entityPersisted = CompleteSurveySectionEntity(
            inputs = setOf(FilledOutHowBadIsThePainWhenIPokeIt.entityTransient)
        )
            .apply { id = this@CompleteHowMuchPainAreYouInSection.id }
        val domain = entityPersisted.toCompleteSurveySection().onFailure { throw it.reason.toMockingbirdException() }
        val dto =  completeSurveySectionToCompleteSurveySectionDtoConverter.convert(domain)
        val completedSurveyInputs = listOf(FilledOutHowBadIsThePainWhenIPokeIt.createDto)
        val createCompletedSurveyInputs = listOf(FilledOutHowBadIsThePainWhenIPokeIt.create)
        val surveyTemplateSectionId = HowMuchPainAreYouInSection.graphqlId
        val createDto = CreateCompleteSurveySectionDto(
            surveyTemplateSectionId = surveyTemplateSectionId,
            completedSurveyInputs = completedSurveyInputs
        )

        val create = object : CreateCompleteSurveySection {
            override val surveyTemplateSectionId = this@CompleteHowMuchPainAreYouInSection.surveyTemplateSectionId
            override val completedSurveyInputs = this@CompleteHowMuchPainAreYouInSection.createCompletedSurveyInputs
        }
    }
}