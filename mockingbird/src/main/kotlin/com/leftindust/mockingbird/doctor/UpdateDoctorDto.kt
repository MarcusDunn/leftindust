package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import java.time.LocalDate

data class UpdateDoctorDto(
    override val did: DoctorDto.DoctorDtoId,
    override val userUid: Updatable<String>,
    override val nameInfo: Updatable<UpdateNameInfo>,
    override val phones: Updatable<List<CreatePhone>>,
    override val title: Updatable<String>,
    override val clinics: Updatable<List<ClinicDto.ClinicDtoId>>,
    override val dateOfBirth: Updatable<LocalDate>,
    override val addresses: Updatable<List<CreateAddress>>,
    override val emails: Updatable<List<CreateEmail>>,
    override val patients: Updatable<List<PatientDto.PatientDtoId>>,
) : UpdateDoctor