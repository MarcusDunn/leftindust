@file:Suppress("ArrayInDataClass")

package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.time.LocalDate
import java.util.*

fun PatientEntity.toPatient(): Result4k<Patient, ConversionError<PatientEntity, Patient>> {
    return Success(
        PatientImpl(
            id = id ?: throw NullEntityIdInConverterException(this),
            thumbnail = thumbnail,
            sex = sex,
            dateOfBirth = dateOfBirth,
            gender = gender,
            ethnicity = ethnicity,
            insuranceNumber = insuranceNumber
        )
    )
}

private data class PatientImpl(
    override val id: UUID,
    override val thumbnail: ByteArray?,
    override val sex: Sex,
    override val dateOfBirth: LocalDate,
    override val gender: String?,
    override val ethnicity: Ethnicity?,
    override val insuranceNumber: String?
) : Patient