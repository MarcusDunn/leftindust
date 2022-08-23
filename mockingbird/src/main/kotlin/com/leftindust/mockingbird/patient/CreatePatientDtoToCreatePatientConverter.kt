package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.CreatePhone
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CreatePatientDtoToCreatePatientConverter : FallibleConverter<CreatePatientDto, CreatePatient> {

    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreatePatientDto): CreatePatient? {
        return CreatePatientImpl(
            nameInfo = source.nameInfo,
            phones = source.phones,
            addresses = source.addresses,
            emails = source.emails,
            dateOfBirth = source.dateOfBirth,
            insuranceNumber = source.insuranceNumber,
            sex = source.sex,
            gender = source.gender,
            ethnicity = source.ethnicity,
            contacts = source.emergencyContacts,
            doctors = source.doctors,
            thumbnail = source.thumbnail
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
        override val gender: String,
        override val ethnicity: Ethnicity?,
        override val contacts: List<CreateContact>,
        override val doctors: List<DoctorDto.DoctorDtoId>,
        override val thumbnail: String?
    ) : CreatePatient
}