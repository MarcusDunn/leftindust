package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.*

fun SurveyTemplateSectionEntity.toSurveyTemplateSection(): Result4k<SurveyTemplateSection, ConversionError<SurveyTemplateSectionEntity, SurveyTemplateSection>> {
    return Success(
        SurveyTemplateSectionImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            title = title,
            subtitle = subtitle,
            calculationId = calculationId
        )
    )
}

private data class SurveyTemplateSectionImpl(
    override val id: UUID,
    override val subtitle: String?,
    override val title: String,
    override val calculationId: Int,
) : SurveyTemplateSection


