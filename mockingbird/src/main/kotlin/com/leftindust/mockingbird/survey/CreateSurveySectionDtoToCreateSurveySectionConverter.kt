package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveySectionDtoToCreateSurveySectionConverter(
    private val createSurveyFieldDtoToCreateSurveyFieldConverter: FallibleConverter<CreateSurveyFieldDto, CreateSurveyField>,
) : FallibleConverter<CreateSurveySectionDto, CreateSurveySection> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveySectionDto): CreateSurveySection? {
        val fields = source.fields.map {
            createSurveyFieldDtoToCreateSurveyFieldConverter.convert(it)
                ?: return null.also { logger.debug { FailedConversionMessage(source) } }
        }
        return object : CreateSurveySection {
            override val name = source.name
            override val number = source.number
            override val description = source.description
            override val fields = fields
        }
    }
}