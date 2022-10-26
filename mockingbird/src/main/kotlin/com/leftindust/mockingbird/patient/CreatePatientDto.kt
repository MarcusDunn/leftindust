package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.contact.CreateContactGraphQlDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmailGraphQlDto
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto
import java.time.LocalDate

data class CreatePatientDto(
    val nameInfo: CreateNameInfoDto,
    val phones: List<CreatePhoneGraphQlDto>,
    val dateOfBirth: LocalDate,
    val addresses: List<CreateAddressGraphQlDto>,
    val emails: List<CreateEmailGraphQlDto>,
    val insuranceNumber: String?,
    val sex: Sex,
    val gender: String?,
    val ethnicity: Ethnicity?,
    val emergencyContacts: List<CreateContactGraphQlDto>,
    val doctors: List<DoctorDto.DoctorDtoId>,
    val thumbnail: String?,
)

