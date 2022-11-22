package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.email.CreateEmailGraphQlDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto
import java.time.LocalDate

data class CreateDoctorDto(
    val user: CreateDoctorUserDto,
    val phones: List<CreatePhoneGraphQlDto>,
    val title: String?,
    val clinic: List<ClinicDto.ClinicDtoId>,
    val dateOfBirth: LocalDate?,
    val addresses: List<CreateAddressGraphQlDto>,
    val emails: List<CreateEmailGraphQlDto>,
    val patients: List<PatientDto.PatientDtoId>,
)
