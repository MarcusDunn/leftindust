package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun CreateSurveyTemplateSectionInputDto.toCreateSurveyTemplateSectionInput(): Result4k<CreateSurveyTemplateSectionInput, ConversionError<CreateSurveyTemplateSectionInputDto, CreateSurveyTemplateSectionInput>> {
    return Success(
        CreateSurveyTemplateSectionInputRestrictionImpl(
            label = label,
            required = required,
            category = category,
            placeholder = placeholder,
            calculationId = calculationId,
            restriction = when (type) {
                SurveyTemplateInputType.Text -> object : CreateSurveyTemplateSectionInputRestriction.Text {}
                SurveyTemplateInputType.Number -> object : CreateSurveyTemplateSectionInputRestriction.Number {}
                SurveyTemplateInputType.Date -> object : CreateSurveyTemplateSectionInputRestriction.Date {}
                SurveyTemplateInputType.Paragraph -> object : CreateSurveyTemplateSectionInputRestriction.Paragraph {}
                SurveyTemplateInputType.Upload -> UploadRestrictionImpl(uploadAccept, uploadMultiple)
                    ?: return ConversionFailure(
                        Exception("Survey template input type was Upload but uploadAccept or uploadMultiple was null")
                    )

                SurveyTemplateInputType.SingleSelect -> SingleSelectRestrictionImpl(options)
                    ?: return ConversionFailure(Exception("Survey Template Input Type was SingleSelect but options was null"))

                SurveyTemplateInputType.MultiSelect -> MultiSelectRestrictionImpl(options) ?: return ConversionFailure(
                    Exception("Survey Template Input Type was MultiSelect but options was null")
                )

                SurveyTemplateInputType.Title -> object : CreateSurveyTemplateSectionInputRestriction.Title {}
            },
        )
    )
}

private class MultiSelectRestrictionImpl private constructor(override val options: List<String>) :
    CreateSurveyTemplateSectionInputRestriction.MultiSelect {
    companion object {
        operator fun invoke(options: List<String>?): MultiSelectRestrictionImpl? {
            return if (!options.isNullOrEmpty()) MultiSelectRestrictionImpl(options) else null
        }
    }
}

private class SingleSelectRestrictionImpl private constructor(override val options: List<String>) :
    CreateSurveyTemplateSectionInputRestriction.SingleSelect {
    companion object {
        operator fun invoke(options: List<String>?): SingleSelectRestrictionImpl? {
            return if (!options.isNullOrEmpty()) SingleSelectRestrictionImpl(options) else null
        }
    }
}

private data class UploadRestrictionImpl(
    override val uploadAccept: TemplateInputUploadType,
    override val uploadMultiple: Boolean
) :
    CreateSurveyTemplateSectionInputRestriction.Upload {
    companion object {
        operator fun invoke(uploadAccept: TemplateInputUploadType?, uploadMultiple: Boolean?): UploadRestrictionImpl? {
            return if (uploadMultiple != null && uploadAccept != null) UploadRestrictionImpl(
                uploadAccept,
                uploadMultiple
            ) else null
        }
    }
}

data class CreateSurveyTemplateSectionInputRestrictionImpl(
    override val label: String,
    override val required: Boolean,
    override val restriction: CreateSurveyTemplateSectionInputRestriction,
    override val placeholder: String?,
    override val category: SurveyTemplateCategory,
    override val calculationId: Int
) : CreateSurveyTemplateSectionInput
