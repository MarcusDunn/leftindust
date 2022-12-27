package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.*
import dev.forkhandles.result4k.onFailure
import java.util.*

object SurveyTemplateMother {
    val surveyTemplateToSurveyTemplateDtoConverter = SurveyTemplateToSurveyTemplateDtoConverter()

    object KoosKneeSurvey {
        val id = UUID.fromString("8fcf8e13-ba61-4216-a3a5-2be88ae27d74")
        val graphqlId = SurveyTemplateDto.SurveyTemplateDtoId(id)
        val title = "KOOS knee survey"
        val subtitle = "the knee'd to know about knees"
        val sectionEntitiesDetached =
            mutableSetOf(SurveyTemplateSectionMother.HowMuchPainAreYouInSection.entityDetached)
        val sectionEntitiesTransient =
            mutableSetOf(SurveyTemplateSectionMother.HowMuchPainAreYouInSection.entityTransient)
        val calculationEntitiesDetached =
            mutableSetOf(SurveyTemplateSectionCalculationMother.FirstCalculation.entityDetached)
        val calculationEntitiesTransient =
            mutableSetOf(SurveyTemplateSectionCalculationMother.FirstCalculation.entityTransient)

        val entityDetached = SurveyTemplateEntity(
            title = title,
            subtitle = subtitle,
            sections = sectionEntitiesDetached,
            calculations = calculationEntitiesDetached,
        ).apply { id = this@KoosKneeSurvey.id }

        val entityTransient = SurveyTemplateEntity(
            title = title,
            subtitle = subtitle,
            sections = sectionEntitiesTransient,
            calculations = calculationEntitiesTransient,
        ).apply { id = this@KoosKneeSurvey.id }

        val domain = entityDetached.toSurveyTemplate().onFailure { throw it.reason.toMockingbirdException() }

        val dto = surveyTemplateToSurveyTemplateDtoConverter.convert(domain)

        val dtoSections = listOf(SurveyTemplateSectionMother.HowMuchPainAreYouInSection.createDto)
        val dtoCalculations = listOf(SurveyTemplateSectionCalculationMother.FirstCalculation.createDto)
        val createDto = CreateSurveyTemplateDto(
            title = this.title,
            subtitle = this.subtitle,
            sections = dtoSections,
            calculations = dtoCalculations,
        )

        val createDomain = createDto.toCreateSurveyTemplate().onFailure { throw it.reason.toMockingbirdException() }!!
    }
}