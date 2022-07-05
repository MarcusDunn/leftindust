package com.leftindust.mockingbird.survey

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
            restriction = when (source.type) {
                SurveyTemplateInputType.Text -> object : CreateSurveyTemplateSectionInputRestriction.Text {}
                SurveyTemplateInputType.Number -> object : CreateSurveyTemplateSectionInputRestriction.Number {}
                SurveyTemplateInputType.Date -> object : CreateSurveyTemplateSectionInputRestriction.Date {}
                SurveyTemplateInputType.Paragraph -> object : CreateSurveyTemplateSectionInputRestriction.Paragraph {}
                SurveyTemplateInputType.Upload -> UploadRestrictionImpl(source.uploadAccept ?: return null.also { FailedConversionMessage(source) })
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

    private data class UploadRestrictionImpl(override val uploadType: TemplateInputUploadType) :
        CreateSurveyTemplateSectionInputRestriction.Upload {}

    data class CreateSurveyTemplateSectionInputRestrictionImpl(
        override val label: String,
        override val required: Boolean,
        override val restriction: CreateSurveyTemplateSectionInputRestriction
    ) : CreateSurveyTemplateSectionInput
}