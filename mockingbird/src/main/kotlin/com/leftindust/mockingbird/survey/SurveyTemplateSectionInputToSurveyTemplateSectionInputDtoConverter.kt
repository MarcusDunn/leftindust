package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter : InfallibleConverter<SurveyTemplateSectionInput, SurveyTemplateSectionInputDto> {
    override fun convert(source: SurveyTemplateSectionInput): SurveyTemplateSectionInputDto {
        return SurveyTemplateSectionInputDto(
            id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id)
        )
    }
}