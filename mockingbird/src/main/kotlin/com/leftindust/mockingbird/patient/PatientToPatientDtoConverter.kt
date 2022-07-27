package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class PatientToPatientDtoConverter : InfallibleConverter<Patient, PatientDto> {
    override fun convert(source: Patient): PatientDto {
        return PatientDto(
            id = PatientDto.PatientDtoId(source.id!!),
            firstName = source.nameInfo.firstName,
            middleName = source.nameInfo.middleName,
            lastName = source.nameInfo.lastName,
            thumbnail = source.thumbnail,
            dateOfBirth = source.dateOfBirth,
            insuranceNumber = source.insuranceNumber,
            sex = source.sex,
            gender = source.gender,
            ethnicity = source.ethnicity,
        )
    }
}