package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveyDtoToCreateSurveyConverter(
    private val createSurveySectionDtoToCreateSurveySectionConverter: FallibleConverter<CreateSurveySectionDto, CreateSurveySection>,
) : FallibleConverter<CreateSurveyDto, CreateSurvey> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveyDto): CreateSurvey? {
        val sections = source.sections
            .map { createSurveySectionDtoToCreateSurveySectionConverter.convert(it) ?: return null.also { logger.trace { FailedConversionMessage(source) } } }
        return object : CreateSurvey {
            override val name = source.name
            override val sections = sections
        }
    }
}