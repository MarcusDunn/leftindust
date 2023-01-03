package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.UUID


fun SurveyTemplateEntity.toSurveyTemplate(): Result4k<SurveyTemplate, ConversionError<SurveyTemplateEntity, SurveyTemplate>> {
    return Success(
        SurveyTemplateImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            title = title,
            subtitle = subtitle,
        )
    )
}

private data class SurveyTemplateImpl(
    override val id: UUID,
    override val title: String,
    override val subtitle: String?,
) : SurveyTemplate