package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.phone.CreatePhoneDto
import java.time.LocalDate

data class CreateDoctorDto(
    val user: CreateDoctorUserDto,
    val phones: List<CreatePhoneDto>,
    val title: String?,
    val clinic: List<ClinicDto.ClinicDtoId>,
    val dateOfBirth: LocalDate?,
    val addresses: List<CreateAddressDto>,
    val emails: List<CreateEmailDto>,
    val patients: List<PatientDto.PatientDtoId>,
)
