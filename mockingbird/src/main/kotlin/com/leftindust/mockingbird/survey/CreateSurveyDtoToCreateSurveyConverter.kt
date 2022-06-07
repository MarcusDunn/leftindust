package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.extensions.doThenNull
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateSurveyDtoToCreateSurveyConverter(
    private val createSurveySectionDtoToCreateSurveySectionConverter: FallibleConverter<CreateSurveySectionDto, CreateSurveySection>,
) : FallibleConverter<CreateSurveyDto, CreateSurvey> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateSurveyDto): CreateSurvey? {
        val sections = buildList {
            for (section in source.sections) {
                add(createSurveySectionDtoToCreateSurveySectionConverter.convert(section)
                    ?: return doThenNull { logger.debug { "failed to convert $source" } })
            }
        }
        return object : CreateSurvey {
            override val name = source.name
            override val sections = sections
        }
    }
}