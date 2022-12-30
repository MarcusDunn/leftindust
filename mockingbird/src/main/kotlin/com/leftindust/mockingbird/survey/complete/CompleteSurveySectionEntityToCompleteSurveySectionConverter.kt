package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.UUID


fun CompleteSurveySectionEntity.toCompleteSurveySection(): Result4k<CompleteSurveySection, ConversionError<CompleteSurveySectionEntity, CompleteSurveySection>> {
    return Success(
        CompleteSurveySectionImpl(
            id = id ?: throw NullEntityIdInConverterException(this)
        )
    )
}

private data class CompleteSurveySectionImpl(override val id: UUID) : CompleteSurveySection