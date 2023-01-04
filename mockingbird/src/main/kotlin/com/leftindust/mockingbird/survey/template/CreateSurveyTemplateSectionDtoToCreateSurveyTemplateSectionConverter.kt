package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure

fun CreateSurveyTemplateSectionDto.toCreateSurveyTemplateSection(): Result4k<CreateSurveyTemplateSection, ConversionError<CreateSurveyTemplateSectionDto, CreateSurveyTemplateSection>> {
    return Success(
        CreateSurveyTemplateSectionImpl(
            title = title,
            subtitle = subtitle,
            inputs = inputs.map {
                it.toCreateSurveyTemplateSectionInput().onFailure { throw it.reason.toMockingbirdException() }
            },
            calculationId = calculationId,
        )
    )
}

private data class CreateSurveyTemplateSectionImpl(
    override val title: String,
    override val subtitle: String?,
    override val inputs: List<CreateSurveyTemplateSectionInput>,
    override val calculationId: Int
) : CreateSurveyTemplateSection

