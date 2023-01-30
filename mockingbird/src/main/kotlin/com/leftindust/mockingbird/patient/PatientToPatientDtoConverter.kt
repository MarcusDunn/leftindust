package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun Patient.toPatientDto(): Result4k<PatientDto, ConversionError<Patient, PatientDto>> {
    return Success(
        PatientDto(
            id = PatientDto.PatientDtoId(id),
            dateOfBirth = dateOfBirth,
            insuranceNumber = insuranceNumber,
            sex = sex,
            gender = gender,
            ethnicity = ethnicity,
        )
    )
}