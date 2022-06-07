package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import java.time.LocalDate
import org.apache.commons.codec.binary.Base64
import org.springframework.stereotype.Component

@Component
class PatientToPatientDtoConverter(
    private val localDateToLocalDateDtoConverter: InfallibleConverter<LocalDate, LocalDateDto>,
) : InfallibleConverter<Patient, PatientDto> {
    override fun convert(source: Patient): PatientDto {
        return PatientDto(
            id = PatientDto.PatientDtoId(source.id!!),
            firstName = source.nameInfo.firstName,
            middleName = source.nameInfo.middleName,
            lastName = source.nameInfo.lastName,
            thumbnail = source.thumbnail?.let { Base64.encodeBase64String(it) },
            dateOfBirth = source.dateOfBirth.let { localDateToLocalDateDtoConverter.convert(it) },
            insuranceNumber = source.insuranceNumber,
            sex = source.sex,
            gender = source.gender,
            ethnicity = source.ethnicity,
        )
    }
}