package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun CompleteSurvey.toCompleteSurveyDto(): Result4k<CompleteSurveyDto, ConversionError<CompleteSurvey, CompleteSurveyDto>> {
    return Success(
        CompleteSurveyDto(
            id = CompleteSurveyDto.CompleteSurveyDtoId(id)
        )
    )
}