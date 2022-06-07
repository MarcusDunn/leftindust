package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateSurveySectionToSurveySectionConverter(
    private val createSurveyFieldToSurveyFieldEntity: InfallibleConverter<CreateSurveyField, SurveyFieldEntity>,
) : InfallibleConverter<CreateSurveySection, SurveySection> {
    override fun convert(source: CreateSurveySection): SurveySection {
        return SurveySection(
            fields = source.fields.map { createSurveyFieldToSurveyFieldEntity.convert(it) }.toSet(),
            description = source.description,
            name = source.name,
            number = source.number,
        )
    }
}