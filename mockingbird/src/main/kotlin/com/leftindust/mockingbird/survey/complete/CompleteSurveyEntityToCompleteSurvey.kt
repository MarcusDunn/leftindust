package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.UUID

fun CompleteSurveyEntity.toCompleteSurvey(): Result4k<CompleteSurvey, ConversionError<CompleteSurveyEntity, CompleteSurvey>> {
    return Success(
        CompleteSurveyImpl(
            id = id ?: throw NullEntityIdInConverterException(this)
        )
    )
}

data class CompleteSurveyImpl(override val id: UUID) : CompleteSurvey

