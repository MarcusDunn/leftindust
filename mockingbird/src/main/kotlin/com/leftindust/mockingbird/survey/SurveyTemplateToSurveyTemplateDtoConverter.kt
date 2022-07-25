package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class SurveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto> {
    override fun convert(source: SurveyTemplate): SurveyTemplateDto {
        return SurveyTemplateDto(
            id = SurveyTemplateDto.SurveyTemplateDtoId(source.id),
            title = source.title,
            subtitle = source.subtitle
        )
    }
}