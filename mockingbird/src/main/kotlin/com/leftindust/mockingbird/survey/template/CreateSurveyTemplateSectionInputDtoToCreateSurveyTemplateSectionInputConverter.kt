package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import org.springframework.stereotype.Component

@Component
class CreateSurveyTemplateSectionInputDtoToCreateSurveyTemplateSectionInputConverter :
    FallibleConverter<CreateSurveyTemplateSectionInputDto, CreateSurveyTemplateSectionInput> {
    override fun convert(source: CreateSurveyTemplateSectionInputDto): CreateSurveyTemplateSectionInput? {

        return CreateSurveyTemplateSectionInputRestrictionImpl(
            label = source.label,
            required = source.required,
            category = source.category,
            placeholder = source.placeholder,
            calculationId = source.calculationId,
            restriction = when (source.type) {
                SurveyTemplateInputType.Text -> object : CreateSurveyTemplateSectionInputRestriction.Text {}
                SurveyTemplateInputType.Number -> object : CreateSurveyTemplateSectionInputRestriction.Number {}
                SurveyTemplateInputType.Date -> object : CreateSurveyTemplateSectionInputRestriction.Date {}
                SurveyTemplateInputType.Paragraph -> object : CreateSurveyTemplateSectionInputRestriction.Paragraph {}
                SurveyTemplateInputType.Upload -> UploadRestrictionImpl(source.uploadAccept, source.uploadMultiple) ?: return null.also { FailedConversionMessage(source) }
                SurveyTemplateInputType.SingleSelect -> SingleSelectRestrictionImpl(source.options) ?: return null.also { FailedConversionMessage(source) }
                SurveyTemplateInputType.MultiSelect -> MultiSelectRestrictionImpl(source.options) ?: return null.also { FailedConversionMessage(source) }
                SurveyTemplateInputType.Title -> object : CreateSurveyTemplateSectionInputRestriction.Title {}
            },
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

    private data class UploadRestrictionImpl(override val uploadAccept: TemplateInputUploadType, override val uploadMultiple: Boolean) :
        CreateSurveyTemplateSectionInputRestriction.Upload {
            companion object {
                operator fun invoke(uploadAccept: TemplateInputUploadType?, uploadMultiple: Boolean?): UploadRestrictionImpl? {
                    return if (uploadMultiple != null && uploadAccept != null) UploadRestrictionImpl(uploadAccept, uploadMultiple) else null
                }
            }
        }

    data class CreateSurveyTemplateSectionInputRestrictionImpl(
        override val label: String,
        override val required: Boolean,
        override val restriction: CreateSurveyTemplateSectionInputRestriction,
        override val placeholder: String?,
        override val category: SurveyTemplateCategory,
        override val calculationId: Int?
    ) : CreateSurveyTemplateSectionInput
}