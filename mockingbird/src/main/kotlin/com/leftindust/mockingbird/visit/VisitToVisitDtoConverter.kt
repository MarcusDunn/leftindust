package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun Visit.toVisitDto(): Result4k<VisitDto, ConversionError<Visit, VisitDto>> {
    return Success(
        VisitDto(
            id = VisitDto.VisitDtoId(id ?: throw NullEntityIdInConverterException(this)),
            title = title,
            description = description
        )
    )
}
