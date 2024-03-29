package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.time.LocalDate
import java.util.*

fun DoctorEntity.toDoctor(): Result4k<Doctor, ConversionError<DoctorEntity, Doctor>> {
    return Success(
        DoctorImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            thumbnail = thumbnail,
            title = title,
            dateOfBirth = dateOfBirth,
        )
    )
}

private data class DoctorImpl(
    override val id: UUID,
    override val thumbnail: ByteArray?,
    override val title: String?,
    override val dateOfBirth: LocalDate?
) : Doctor