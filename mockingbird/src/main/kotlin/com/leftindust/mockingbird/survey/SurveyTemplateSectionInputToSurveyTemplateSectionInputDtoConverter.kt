package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionInputToSurveyTemplateSectionInputDtoConverter : InfallibleConverter<SurveyTemplateSectionInput, SurveyTemplateSectionInputDto> {
    override fun convert(source: SurveyTemplateSectionInput): SurveyTemplateSectionInputDto {
        return when (val restriction = source.restriction) {
            is DateSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.Date,
                label = source.label,
                options = null,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is MultiSelectSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.MultiSelect,
                label = source.label,
                options = restriction.options,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is NumberSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.Number,
                label = source.label,
                options = null,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is ParagraphSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.Paragraph,
                label = source.label,
                options = null,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is SingleSelectSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.SingleSelect,
                label = source.label,
                options = restriction.options,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is TextSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.Text,
                label = source.label,
                options = null,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is TitleSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.Title,
                label = source.label,
                options = null,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = null,
                uploadAccept = null,
            )
            is UploadSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(source.id),
                type = SurveyTemplateInputType.Upload,
                label = source.label,
                options = null,
                placeholder = source.placeholder,
                required = source.required,
                category = source.category,
                uploadMultiple = restriction.uploadMultiple,
                uploadAccept = restriction.uploadAccept,
            )
        }
    }
}