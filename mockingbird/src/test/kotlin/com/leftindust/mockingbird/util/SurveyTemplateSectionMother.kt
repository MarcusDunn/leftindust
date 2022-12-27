package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.template.*
import com.leftindust.mockingbird.util.SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput
import dev.forkhandles.result4k.onFailure
import java.util.*

object SurveyTemplateSectionMother {
    val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto> =
        SurveyTemplateSectionToSurveyTemplateSectionDtoConverter()

    object HowMuchPainAreYouInSection {
        val id = UUID.fromString("32b0b8ba-7f8f-4c7d-bad9-23bea9a024ba")
        val graphqlId = SurveyTemplateSectionDto.SurveyTemplateSectionDtoId(id)
        val title = "Section the first!"
        val subtitle = "The first section"
        val calculationId = 0

        val createDto: CreateSurveyTemplateSectionDto = CreateSurveyTemplateSectionDto(
            title = title,
            subtitle = subtitle,
            inputs = listOf(HowMuchPainAreYouInSectionInput.inputDto),
            calculationId = calculationId,
        )
        val entityDetached = SurveyTemplateSectionEntity(
            index = 0,
            title = title,
            subtitle = "The first section",
            inputs = listOf(HowMuchPainAreYouInSectionInput.entityPersisted).toMutableSet(),
            calculationId = calculationId,
        ).apply { this.id = this@HowMuchPainAreYouInSection.id }

        val entityTransient = SurveyTemplateSectionEntity(
            index = 0,
            title = title,
            subtitle = "The first section",
            inputs = listOf(HowMuchPainAreYouInSectionInput.entityDetached).toMutableSet(),
            calculationId = calculationId,
        )

        val domain: SurveyTemplateSection =
            entityDetached.toSurveyTemplateSection().onFailure { throw it.reason.toMockingbirdException() }

        val dto: SurveyTemplateSectionDto = surveyTemplateToSurveyTemplateDtoConverter.convert(domain)
    }

}