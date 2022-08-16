package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionEntityToSurveyTemplateSectionConverter : InfallibleConverter<SurveyTemplateSectionEntity, SurveyTemplateSection> {
    override fun convert(source: SurveyTemplateSectionEntity): SurveyTemplateSection {
        return SurveyTemplateSectionImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            title = source.title,
            subtitle = source.subtitle,
            calculationId = source.calculationId
        )
    }

    private data class SurveyTemplateSectionImpl(
        override val id: UUID,
        override val subtitle: String?,
        override val title: String,
        override val calculationId: Int,
    ) : SurveyTemplateSection
}