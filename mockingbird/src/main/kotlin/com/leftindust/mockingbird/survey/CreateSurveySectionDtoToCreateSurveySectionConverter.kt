package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.extensions.doThenNull
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveySectionDtoToCreateSurveySectionConverter(
    private val createSurveyFieldDtoToCreateSurveyFieldConverter: FallibleConverter<CreateSurveyFieldDto, CreateSurveyField>,
) : FallibleConverter<CreateSurveySectionDto, CreateSurveySection> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveySectionDto): CreateSurveySection? {
        val fields = buildList {
            for (field in source.fields) {
                add(createSurveyFieldDtoToCreateSurveyFieldConverter.convert(field)
                    ?: return doThenNull { logger.debug { "failed to convert $source" } })
            }
        }
        return object : CreateSurveySection {
            override val name = source.name
            override val number = source.number
            override val description = source.description
            override val fields = fields
        }
    }
}