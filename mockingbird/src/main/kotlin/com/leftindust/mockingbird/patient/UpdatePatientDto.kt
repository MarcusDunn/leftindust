package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.contact.CreateContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.person.UpdateNameInfoDto
import com.leftindust.mockingbird.phone.CreatePhoneDto
import java.time.LocalDate

data class UpdatePatientDto(
    val pid: PatientDto.PatientDtoId,
    val nameInfo: UpdateNameInfoDto,
    val phones: List<CreatePhoneDto>,
    val dateOfBirth: LocalDate,
    val addresses: List<CreateAddressDto>,
    val emails: List<CreateEmailDto>,
    val insuranceNumber: String?,
    val sex: Sex,
    val gender: String?,
    val ethnicity: Ethnicity?,
    val emergencyContacts: List<CreateContactDto>,
    val doctors: List<DoctorDto.DoctorDtoId>,
    val thumbnail: String?,
)


