package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun SurveyLink.toSurveyLinkDto(): Result4k<SurveyLinkDto, ConversionError<SurveyLink, SurveyLinkDto>> {
    return Success(
        SurveyLinkDto(
            id = SurveyLinkDto.SurveyLinkDtoId(id),
        )
    )
}

