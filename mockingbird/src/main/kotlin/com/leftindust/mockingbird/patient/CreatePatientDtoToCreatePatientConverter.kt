package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.ConversionError.Companion.ConversionFailure
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.toCreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.toCreateEmail
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.CreatePhone
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import java.time.LocalDate
import mu.KotlinLogging
import org.springframework.stereotype.Component

fun CreatePatientDto.toCreatePatient(): Result4k<CreatePatient, ConversionError<CreatePatientDto, CreatePatient>> {
    return Success(
        CreatePatientImpl(
            nameInfo = nameInfo,
            phones = phones,
            addresses = addresses,
            emails = emails.map {
                it.toCreateEmail()
                    .onFailure { e -> return ConversionFailure(e.reason) }
            },

            dateOfBirth = dateOfBirth,
            insuranceNumber = insuranceNumber,
            sex = sex,
            gender = gender,
            ethnicity = ethnicity,
            contacts = emergencyContacts.map {
                it.toCreateContact()
                    .onFailure { e -> return ConversionFailure(e.reason) }
            },
            doctors = doctors,
            thumbnail = thumbnail
        )
    )
}

    private data class CreatePatientImpl(
        override val nameInfo: CreateNameInfoDto,
        override val phones: List<CreatePhone>,
        override val dateOfBirth: LocalDate,
        override val addresses: List<CreateAddress>,
        override val emails: List<CreateEmail>,
        override val insuranceNumber: String?,
        override val sex: Sex,
        override val gender: String?,
        override val ethnicity: Ethnicity?,
        override val contacts: List<CreateContact>,
        override val doctors: List<DoctorDto.DoctorDtoId>,
        override val thumbnail: String?
    ) : CreatePatient
}