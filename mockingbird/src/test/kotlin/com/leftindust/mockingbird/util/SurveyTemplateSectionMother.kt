package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSection
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionInput
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionInputDto
import com.leftindust.mockingbird.survey.template.CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateSection
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionEntity
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionEntityToSurveyTemplateSectionConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionToSurveyTemplateSectionDtoConverter
import com.leftindust.mockingbird.util.SurveyTemplateSectionInputMother.HowMuchPainAreYouInSectionInput
import java.util.UUID

object SurveyTemplateSectionMother {
    private val createSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter: FallibleConverter<CreateSurveyTemplateSectionInputDto, CreateSurveyTemplateSectionInput> = CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter()
    val createSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter: FallibleConverter<CreateSurveyTemplateSectionDto, CreateSurveyTemplateSection> = CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter(createSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter)
    val surveyTemplateSectionEntityToSurveyTemplateSectionConverter: InfallibleConverter<SurveyTemplateSectionEntity, SurveyTemplateSection> = SurveyTemplateSectionEntityToSurveyTemplateSectionConverter()
    val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto> = SurveyTemplateSectionToSurveyTemplateSectionDtoConverter()

    object HowMuchPainAreYouInSection {
        val id = UUID.fromString("32b0b8ba-7f8f-4c7d-bad9-23bea9a024ba")
        val graphqlId = SurveyTemplateSectionDto.SurveyTemplateSectionDtoId(id)
        val title = "Section the first!"
        val subtitle = "The first section"

        val createDto: CreateSurveyTemplateSectionDto = CreateSurveyTemplateSectionDto(
            title = title,
            subtitle = subtitle,
            inputs = listOf(HowMuchPainAreYouInSectionInput.inputDto)
        )
        val entityPersisted: SurveyTemplateSectionEntity = SurveyTemplateSectionEntity(
            index = 0,
            title = title,
            subtitle = "The first section",
            inputs = listOf(HowMuchPainAreYouInSectionInput.entityPersisted).toMutableSet()
        ).apply { this.id = this@HowMuchPainAreYouInSection.id }

        val entityUnpersited: SurveyTemplateSectionEntity
            get() = entityPersisted.apply {
                id = null
                inputs.map { it.apply { id = null } }
            }

        val domain: SurveyTemplateSection = surveyTemplateSectionEntityToSurveyTemplateSectionConverter.convert(entityPersisted)

        val dto: SurveyTemplateSectionDto = surveyTemplateToSurveyTemplateDtoConverter.convert(domain)
    }

}