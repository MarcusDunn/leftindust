package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun CompleteSurveySection.toCompleteSurveySectionDto(): Result4k<CompleteSurveySectionDto, ConversionError<CompleteSurveySection, CompleteSurveySectionDto>> {
    return Success(
        CompleteSurveySectionDto(
            id = CompleteSurveySectionDto.CompleteSurveySectionDtoId(id)
        )
    )
}