package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateDto
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateDtoToCreateSurveyTemplateConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateEntityToSurveyTemplateConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateToSurveyTemplateDtoConverter
import java.util.UUID

object SurveyTemplateMother {
    val surveyTemplateEntityToSurveyTemplateConverter = SurveyTemplateEntityToSurveyTemplateConverter()
    val surveyTemplateToSurveyTemplateDtoConverter = SurveyTemplateToSurveyTemplateDtoConverter()
    val createSurveyTemplateDtoToCreateSurveyTemplateConverter = CreateSurveyTemplateDtoToCreateSurveyTemplateConverter(SurveyTemplateSectionMother.createSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter, SurveySectionTemplateCalculationMother.createSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter)

    object KoosKneeSurvey {
        val id = UUID.fromString("8fcf8e13-ba61-4216-a3a5-2be88ae27d74")
        val graphqlId = SurveyTemplateDto.SurveyTemplateDtoId(id)
        val title = "KOOS knee survey"
        val subtitle = "the knee'd to know about knees"
        val sectionEntities = mutableSetOf(SurveyTemplateSectionMother.HowMuchPainAreYouInSection.entityPersisted)
        val calculationEntities = mutableSetOf(SurveySectionTemplateCalculationMother.FirstCalculation.entityPersisted)
        val entityPersisted = SurveyTemplateEntity(
            title = title,
            subtitle = subtitle,
            sections = sectionEntities,
            calculations = calculationEntities,
        ).apply { id = this@KoosKneeSurvey.id }

        val entityUnpersisted: SurveyTemplateEntity
            get() = entityPersisted.apply {
                id = null
                sections.forEach {
                    it.id = null
                    it.inputs.forEach { it.id = null }
                }
                calculations.forEach { it.id = null }
            }

        val domain = surveyTemplateEntityToSurveyTemplateConverter.convert(entityPersisted)

        val dto = surveyTemplateToSurveyTemplateDtoConverter.convert(domain)

        val dtoSections = listOf(SurveyTemplateSectionMother.HowMuchPainAreYouInSection.createDto)
        val dtoCalculations = listOf(SurveySectionTemplateCalculationMother.FirstCalculation.createDto)
        val createDto = CreateSurveyTemplateDto(
            title = this.title,
            subtitle = this.subtitle,
            sections = dtoSections,
            calculations = dtoCalculations,
        )

        val createDomain = createSurveyTemplateDtoToCreateSurveyTemplateConverter.convert(createDto)!!
    }
}