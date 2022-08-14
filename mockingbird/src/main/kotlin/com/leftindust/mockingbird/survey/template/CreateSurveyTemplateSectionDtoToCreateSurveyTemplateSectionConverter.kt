package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveyTemplateSectionDtoToCreateSurveyTemplateSectionConverter(
    private val createSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter: FallibleConverter<CreateSurveyTemplateSectionInputDto, CreateSurveyTemplateSectionInput>
) : FallibleConverter<CreateSurveyTemplateSectionDto, CreateSurveyTemplateSection> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveyTemplateSectionDto): CreateSurveyTemplateSection? {
        return CreateSurveyTemplateSectionImpl(
            title = source.title,
            subtitle = source.subtitle,
            inputs = source.inputs.map {
                createSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter.convert(
                    it
                ) ?: return null.also { logger.trace { FailedConversionMessage(source) } }
            },
            calculationId = source.calculationId,
        )
    }

    private data class CreateSurveyTemplateSectionImpl(
        override val title: String,
        override val subtitle: String?,
        override val inputs: List<CreateSurveyTemplateSectionInput>,
        override val calculationId: Int?
    ) : CreateSurveyTemplateSection
}