package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun SurveyTemplateCalculation.toSurveyTemplateCalculationDto(): Result4k<SurveyTemplateCalculationDto, ConversionError<SurveyTemplateCalculation, SurveyTemplateCalculationDto>> {
    return Success(
        SurveyTemplateCalculationDto(
            id = SurveyTemplateCalculationDto.SurveyTemplateCalculationDtoId(id),
            index = index,
            label = label,
            inputType = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        )
    )
}