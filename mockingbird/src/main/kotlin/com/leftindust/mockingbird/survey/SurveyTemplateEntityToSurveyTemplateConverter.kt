package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SurveyTemplateEntityToSurveyTemplateConverter : InfallibleConverter<SurveyTemplateEntity, SurveyTemplate> {
    override fun convert(source: SurveyTemplateEntity): SurveyTemplate {
        return SurveyTemplateImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            title = source.title,
            subtitle = source.subtitle,
        )
    }

    private data class SurveyTemplateImpl(
        override val id: UUID,
        override val title: String,
        override val subtitle: String?,
    ) : SurveyTemplate
}