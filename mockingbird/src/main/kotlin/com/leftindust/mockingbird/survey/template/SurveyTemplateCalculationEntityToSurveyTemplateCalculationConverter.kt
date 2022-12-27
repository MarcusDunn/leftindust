package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.*


fun SurveyTemplateCalculationEntity.toSurveyTemplateCalculation(): Result4k<SurveyTemplateCalculation, ConversionError<SurveyTemplateCalculationEntity, SurveyTemplateCalculation>> {
    return Success(
        SurveyTemplateCalculationImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            index = index,
            label = label,
            inputType = inputType,
            showOnComplete = showOnComplete,
            calculation = calculation,
        )
    )
}

private data class SurveyTemplateCalculationImpl(
    override val id: UUID,
    override val index: Int,
    override val label: String,
    override val inputType: SurveyTemplateInputType,
    override val showOnComplete: Boolean,
    override val calculation: String,
) : SurveyTemplateCalculation