package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SurveyTemplateSectionEntityToSurveyTemplateSectionConverter : InfallibleConverter<SurveyTemplateSectionEntity, SurveyTemplateSection> {
    override fun convert(source: SurveyTemplateSectionEntity): SurveyTemplateSection {
        return SurveyTemplateSectionImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source)
        )
    }

    private data class SurveyTemplateSectionImpl(override val id: UUID) : SurveyTemplateSection
}