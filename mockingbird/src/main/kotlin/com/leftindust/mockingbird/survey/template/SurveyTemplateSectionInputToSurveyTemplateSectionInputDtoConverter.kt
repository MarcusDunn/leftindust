package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun SurveyTemplateSectionInput.toSurveyTemplateSectionInputDto(): Result4k<SurveyTemplateSectionInputDto, ConversionError<SurveyTemplateSectionInput, SurveyTemplateSectionInputDto>> {
    return Success(
        when (val restriction = restriction) {
            is DateSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.Date,
                label = label,
                options = null,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId
            )

            is MultiSelectSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.MultiSelect,
                label = label,
                options = restriction.options,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId
            )

            is NumberSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.Number,
                label = label,
                options = null,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId,
            )

            is ParagraphSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.Paragraph,
                label = label,
                options = null,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId,

                )

            is SingleSelectSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.SingleSelect,
                label = label,
                options = restriction.options,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId,

                )

            is TextSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.Text,
                label = label,
                options = null,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId,

                )

            is TitleSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.Title,
                label = label,
                options = null,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = null,
                uploadAccept = null,
                calculationId = calculationId,

                )

            is UploadSurveyTemplateSectionInputRestriction -> SurveyTemplateSectionInputDto(
                id = SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId(id),
                type = SurveyTemplateInputType.Upload,
                label = label,
                options = null,
                placeholder = placeholder,
                required = required,
                category = category,
                uploadMultiple = restriction.uploadMultiple,
                uploadAccept = restriction.uploadAccept,
                calculationId = calculationId,
            )
        }
    )
}