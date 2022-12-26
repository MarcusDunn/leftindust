package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun CreateSurveyTemplateCalculationDto.toCreateSurveyTemplateCalculationConverter(): Result4k<CreateSurveyTemplateCalculation, ConversionError<CreateSurveyTemplateCalculationDto, CreateSurveyTemplateCalculation>> {
    return Success(
        CreateSurveyTemplateCalculationImpl(
            label = label,
            type = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        )
    )
}

private data class CreateSurveyTemplateCalculationImpl(
    override val label: String,
    override val type: SurveyTemplateInputType,
    override val showOnComplete: Boolean,
    override val calculation: String
) : CreateSurveyTemplateCalculation