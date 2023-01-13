package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.UUID

fun SurveyLinkEntity.toSurveyLink(): Result4k<SurveyLink, ConversionError<SurveyLinkEntity, SurveyLink>> {
    return Success(
        SurveyLinkImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
        )
    )
}

private data class SurveyLinkImpl(
    override val id: UUID,
) : SurveyLink
