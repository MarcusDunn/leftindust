package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteSurveyEntityToCompleteSurvey : InfallibleConverter<CompleteSurveyEntity, CompleteSurvey>{
    override fun convert(source: CompleteSurveyEntity): CompleteSurvey {
        return CompleteSurveyImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source)
        )
    }

    data class CompleteSurveyImpl(override val id: UUID) : CompleteSurvey
}
