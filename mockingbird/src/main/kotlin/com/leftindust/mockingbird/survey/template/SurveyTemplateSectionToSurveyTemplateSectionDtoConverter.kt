package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun SurveyTemplateSection.toSurveyTemplateSectionDto(): Result4k<SurveyTemplateSectionDto, ConversionError<SurveyTemplateSection, SurveyTemplateSectionDto>> {
    return Success(
        SurveyTemplateSectionDto(
            id = SurveyTemplateSectionDto.SurveyTemplateSectionDtoId(id),
            title = title,
            subtitle = subtitle,
            calculationId = calculationId,
        )
    )
}