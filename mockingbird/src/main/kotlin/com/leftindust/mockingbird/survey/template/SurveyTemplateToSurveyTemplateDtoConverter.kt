package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun SurveyTemplate.toSurveyTemplateDto(): Result4k<SurveyTemplateDto, ConversionError<SurveyTemplate, SurveyTemplateDto>> {
    return Success(
        SurveyTemplateDto(
            id = SurveyTemplateDto.SurveyTemplateDtoId(id),
            title = title,
            subtitle = subtitle
        )
    )
}