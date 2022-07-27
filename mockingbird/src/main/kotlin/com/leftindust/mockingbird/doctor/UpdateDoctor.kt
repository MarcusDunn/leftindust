package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import java.time.LocalDate

interface UpdateDoctor {
    val did: DoctorDto.DoctorDtoId
    val userUid: Deletable<String>
    val nameInfo: Updatable<UpdateNameInfo>
    val phones: Updatable<List<CreatePhone>>
    val title: Updatable<String>
    val clinics: Updatable<List<ClinicDto.ClinicDtoId>>
    val dateOfBirth: Updatable<LocalDate>
    val addresses: Updatable<List<CreateAddress>>
    val emails: Updatable<List<CreateEmail>>
    val patients: Updatable<List<PatientDto.PatientDtoId>>
}