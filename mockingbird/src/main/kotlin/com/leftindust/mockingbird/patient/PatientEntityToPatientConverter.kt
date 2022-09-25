package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import java.time.LocalDate
import java.util.UUID

class PatientEntityToPatientConverter : InfallibleConverter<PatientEntity, Patient> {
    override fun convert(source: PatientEntity): Patient {
        return PatientImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            nameInfo = source.nameInfo,
            thumbnail = source.thumbnail,
            sex = source.sex,
            dateOfBirth = source.dateOfBirth,
            gender = source.gender,
            ethnicity = source.ethnicity,
            insuranceNumber = source.insuranceNumber
                ?: throw Exception("Insurance Number was not found for this Patient Id: ${source.id}")
        )
    }

    private data class PatientImpl(
        override val id: UUID,
        override val nameInfo: NameInfo,
        override val thumbnail: ByteArray?,
        override val sex: Sex,
        override val dateOfBirth: LocalDate,
        override val gender: String,
        override val ethnicity: Ethnicity?,
        override val insuranceNumber: String
    ) : Patient
}