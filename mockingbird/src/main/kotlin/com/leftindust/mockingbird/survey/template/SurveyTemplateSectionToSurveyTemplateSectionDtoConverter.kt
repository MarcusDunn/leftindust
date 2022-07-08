package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionToSurveyTemplateSectionDtoConverter : InfallibleConverter<SurveyTemplateSection, SurveyTemplateSectionDto> {
    override fun convert(source: SurveyTemplateSection): SurveyTemplateSectionDto {
        return SurveyTemplateSectionDto(
            id = SurveyTemplateSectionDto.SurveyTemplateSectionDtoId(source.id),
            title = source.title,
            subtitle = source.subtitle,
        )
    }
}