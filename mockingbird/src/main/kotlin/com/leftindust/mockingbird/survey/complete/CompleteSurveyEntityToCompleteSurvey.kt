package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteSurveyEntityToCompleteSurvey(
    private val completeSurveySectionEntityToCompleteSurveySectionConverter: InfallibleConverter<CompleteSurveySectionEntity, CompleteSurveySection>
) : InfallibleConverter<CompleteSurveyEntity, CompleteSurvey> {
    override fun convert(source: CompleteSurveyEntity): CompleteSurvey {
        return CompleteSurveyImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            sections = source.sections.map { completeSurveySectionEntityToCompleteSurveySectionConverter.convert(it) }
        )
    }

    data class CompleteSurveyImpl(
        override val id: UUID,
        override val sections: List<CompleteSurveySection>
    ) : CompleteSurvey
}
