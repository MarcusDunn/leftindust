package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.*


fun SurveyTemplateSectionInputEntity.toSurveyTemplateSectionInput(): Result4k<SurveyTemplateSectionInput, ConversionError<SurveyTemplateSectionInputEntity, SurveyTemplateSectionInput>> {
    return Success(
        SurveyTemplateSectionInputImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            category = category,
            required = required,
            placeholder = placeholder,
            label = label,
            restriction = when (type) {
                SurveyTemplateInputType.Text -> object : TextSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Number -> object : NumberSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Date -> object : DateSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Paragraph -> object : ParagraphSurveyTemplateSectionInputRestriction {}
                SurveyTemplateInputType.Upload -> UploadSurveyTemplateSectionInputRestrictionImpl(
                    uploadAccept,
                    uploadMultiple
                ) ?: return ConversionFailure(
                    Exception("Survey template input type was Upload but uploadAccept or uploadMultiple was null")
                )

                SurveyTemplateInputType.SingleSelect -> SingleSelectSurveyTemplateSectionInputRestrictionImpl(
                    options ?: return ConversionFailure(
                        Exception("Survey template input type was SingleSelect but options was null")
                    )
                )

                SurveyTemplateInputType.MultiSelect -> MultiSelectSurveyTemplateSectionInputRestrictionImpl(
                    options ?: return ConversionFailure(
                        Exception("Survey Template Input Type was MultiSelect but options was null")
                    )
                )

                SurveyTemplateInputType.Title -> object : TitleSurveyTemplateSectionInputRestriction {}
            },
            calculationId = calculationId,
        )
    )
}

data class SurveyTemplateSectionInputImpl(
    override val id: UUID,
    override val category: SurveyTemplateCategory,
    override val required: Boolean,
    override val placeholder: String?,
    override val label: String,
    override val restriction: SurveyTemplateSectionInputRestriction,
    override val calculationId: Int,
) : SurveyTemplateSectionInput

private data class MultiSelectSurveyTemplateSectionInputRestrictionImpl(override val options: List<String>) :
    MultiSelectSurveyTemplateSectionInputRestriction {}

private data class SingleSelectSurveyTemplateSectionInputRestrictionImpl(override val options: List<String>) :
    SingleSelectSurveyTemplateSectionInputRestriction

private data class UploadSurveyTemplateSectionInputRestrictionImpl(
    override val uploadAccept: TemplateInputUploadType,
    override val uploadMultiple: Boolean
) :
    UploadSurveyTemplateSectionInputRestriction {
    companion object {
        operator fun invoke(
            uploadAccept: TemplateInputUploadType?,
            uploadMultiple: Boolean?
        ): UploadSurveyTemplateSectionInputRestrictionImpl? {
            return if (uploadMultiple != null && uploadAccept != null) {
                UploadSurveyTemplateSectionInputRestrictionImpl(uploadAccept, uploadMultiple)
            } else {
                null
            }
        }
    }
}