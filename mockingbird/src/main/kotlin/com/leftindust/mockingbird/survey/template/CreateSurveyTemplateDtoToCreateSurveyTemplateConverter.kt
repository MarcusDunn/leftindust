package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure

fun CreateSurveyTemplateDto.toCreateSurveyTemplate(): Result4k<CreateSurveyTemplate, ConversionError<CreateSurveyTemplateDto, CreateSurveyTemplate>> {
    return Success(
        CreateSurveyTemplateImpl(
            title = title,
            subtitle = subtitle,
            sections = sections.map {
                it.toCreateSurveyTemplateSection().onFailure { throw it.reason.toMockingbirdException() }
            },
            calculations = calculations.map {
                it.toCreateSurveyTemplateCalculation().onFailure { throw it.reason.toMockingbirdException() }
            },
        )
    )
}


private data class CreateSurveyTemplateImpl(
    override val title: String,
    override val subtitle: String?,
    override val sections: List<CreateSurveyTemplateSection>,
    override val calculations: List<CreateSurveyTemplateCalculation>
) : CreateSurveyTemplate

