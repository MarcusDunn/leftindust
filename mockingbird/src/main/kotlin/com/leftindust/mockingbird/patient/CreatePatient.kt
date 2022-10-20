package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.CreatePhone
import java.time.LocalDate

interface CreatePatient {
    val nameInfo: CreateNameInfoDto
    val phones: List<CreatePhone>
    val dateOfBirth: LocalDate
    val addresses: List<CreateAddress>
    val emails: List<CreateEmail>
    val insuranceNumber: String?
    val sex: Sex
    val gender: String?
    val ethnicity: Ethnicity?
    val contacts: List<CreateContact>
    val doctors: List<DoctorDto.DoctorDtoId>
    val thumbnail: String?
}