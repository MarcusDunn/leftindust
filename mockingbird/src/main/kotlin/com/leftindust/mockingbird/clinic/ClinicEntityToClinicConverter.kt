package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.*


fun ClinicEntity.toClinic(): Result4k<Clinic, ConversionError<ClinicEntity, Clinic>> {
    return Success(
        ClinicImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            name = name
        )
    )

}

private data class ClinicImpl(
    override val id: UUID,
    override val name: String
) : Clinic