package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter : FallibleConverter<SurveyTemplateSectionInputEntity, SurveyTemplateSectionInput> {
    override fun convert(source: SurveyTemplateSectionInputEntity): SurveyTemplateSectionInput? {
        return SurveyTemplateSectionInputImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            category = source.category,
            required = source.required,
            placeholder = source.placeholder,
            label = source.label,
            restriction = when (source.type) {
                SurveyTemplateInputType.Text -> object : TextSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Number -> object : NumberSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Date -> object : DateSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Paragraph -> object : ParagraphSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Upload -> UploadSurveyTemplateSectionInputRestrictionImpl(source.uploadAccept, source.uploadMultiple) ?: return null
                SurveyTemplateInputType.SingleSelect -> SingleSelectSurveyTemplateSectionInputRestrictionImpl(source.options ?: return null)
                SurveyTemplateInputType.MultiSelect -> MultiSelectSurveyTemplateSectionInputRestrictionImpl(source.options ?: return null)
                SurveyTemplateInputType.Title -> object : TitleSurveyTemplateSectionInputRestriction {}
            }
        )
    }

    private data class MultiSelectSurveyTemplateSectionInputRestrictionImpl(override val options: List<String>) : MultiSelectSurveyTemplateSectionInputRestriction {}

    private data class SingleSelectSurveyTemplateSectionInputRestrictionImpl(override val options: List<String>) : SingleSelectSurveyTemplateSectionInputRestriction

    private data class UploadSurveyTemplateSectionInputRestrictionImpl(override val uploadAccept: TemplateInputUploadType, override val uploadMultiple: Boolean) :
        UploadSurveyTemplateSectionInputRestriction {
        companion object {
            operator fun invoke(uploadAccept: TemplateInputUploadType?, uploadMultiple: Boolean?): UploadSurveyTemplateSectionInputRestrictionImpl? {
                return if (uploadMultiple != null && uploadAccept != null) {
                    UploadSurveyTemplateSectionInputRestrictionImpl(uploadAccept, uploadMultiple)
                } else {
                    null
                }
            }
        }
    }

    data class SurveyTemplateSectionInputImpl(
        override val id: UUID,
        override val category: SurveyTemplateCategory,
        override val required: Boolean,
        override val placeholder: String?,
        override val label: String,
        override val restriction: SurveyTemplateSectionInputRestriction,
    ) : SurveyTemplateSectionInput
}