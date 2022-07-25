package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter : InfallibleConverter<SurveyTemplateSectionInput, SurveyTemplateSectionInputDto> {
    override fun convert(source: SurveyTemplateSectionInput): SurveyTemplateSectionInputDto {
        return SurveyTemplateSectionInputDto(
            id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
            type = source.type,
            label = source.label,
            options = source.options,
            placeholder = source.placeholder,
            required = source.required,
            category = source.category,
            uploadMultiple = source.uploadMultiple,
            uploadAccept = source.uploadAccept,
        )
    }
}