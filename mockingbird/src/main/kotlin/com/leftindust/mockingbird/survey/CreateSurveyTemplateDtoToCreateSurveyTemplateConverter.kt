package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InfallibleConverter
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveyTemplateDtoToCreateSurveyTemplateConverter(
    private val createSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter: FallibleConverter<CreateSurveyTemplateSectionDto, CreateSurveyTemplateSection>,
    private val createSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter: InfallibleConverter<CreateSurveyTemplateCalculationDto, CreateSurveyTemplateCalculation>
) :
    FallibleConverter<CreateSurveyTemplateDto, CreateSurveyTemplate> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveyTemplateDto): CreateSurveyTemplate? {
        return CreateSurveyTemplateImpl(
            title = source.title,
            subtitle = source.subtitle,
            sections = source.sections.map { createSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter.convert(it)  ?: return null.also { logger.trace { FailedConversionMessage(source) } } },
            calculations = source.calculations.map { createSurveyTemplateCalculationDtoToCreateSurveyTemplateCalculationConverter.convert(it) },
        )
    }

    private data class CreateSurveyTemplateImpl(
        override val title: String,
        override val subtitle: String?,
        override val sections: List<CreateSurveyTemplateSection>,
        override val calculations: List<CreateSurveyTemplateCalculation>
    ) : CreateSurveyTemplate
}
