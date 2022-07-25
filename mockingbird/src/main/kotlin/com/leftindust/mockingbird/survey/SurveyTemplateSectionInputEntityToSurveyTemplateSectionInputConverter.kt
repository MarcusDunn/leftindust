package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter : InfallibleConverter<SurveyTemplateSectionInputEntity, SurveyTemplateSectionInput> {
    override fun convert(source: SurveyTemplateSectionInputEntity): SurveyTemplateSectionInput {
        return SurveyTemplateSectionInputImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            uploadAccept = source.uploadAccept,
            uploadMultiple = source.uploadMultiple,
            category = source.category,
            required = source.required,
            placeholder = source.placeholder,
            options = source.options,
            label = source.label,
            type = source.type,
        )
    }

    data class SurveyTemplateSectionInputImpl(
        override val id: UUID,
        override val uploadAccept: TemplateInputUploadType?,
        override val uploadMultiple: Boolean?,
        override val category: SurveyTemplateCategory,
        override val required: Boolean,
        override val placeholder: String?,
        override val options: List<String>?,
        override val label: String,
        override val type: SurveyTemplateInputType,
    ) : SurveyTemplateSectionInput
}