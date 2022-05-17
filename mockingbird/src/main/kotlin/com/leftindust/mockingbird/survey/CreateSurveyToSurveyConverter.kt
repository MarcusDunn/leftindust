package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateSurveyToSurveyConverter(
    private val createSurveySectionToSurveySectionConverter: InfallibleConverter<CreateSurveySection, SurveySection>,
) : InfallibleConverter<CreateSurvey, Survey> {
    override fun convert(source: CreateSurvey): Survey {
        return Survey(
            source.name,
            source.sections.map { createSurveySectionToSurveySectionConverter.convert(it) }.toSet())
    }
}