package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.user.MediqGroupDto
import com.leftindust.mockingbird.user.MediqUserDto
import java.time.LocalDate

interface CreateDoctor {
    val user: User
    val phones: List<CreatePhone>
    val title: String?
    val clinic: List<ClinicDto.ClinicDtoId>
    val dateOfBirth: LocalDate?
    val addresses: List<CreateAddress>
    val emails: List<CreateEmail>
    val patients: List<PatientDto.PatientDtoId>

    sealed class User {
        data class NoUser(val nameInfo: CreateNameInfo) : User()
        data class Find(val userUid: String) : User()
        data class Create(
            val uid: MediqUserDto.MediqUserUniqueId,
            val nameInfo: CreateNameInfo,
            val group: MediqGroupDto.MediqGroupId,
        ) : User()
    }
}

