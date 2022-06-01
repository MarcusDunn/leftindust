package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.user.MediqGroupDto

data class CreateDoctorDto(
    override val user: CreateDoctor.User,
    override val phones: List<CreatePhoneDto>?,
    override val title: String?,
    override val clinic: List<ClinicDto.ClinicDtoId>,
    override val dateOfBirth: LocalDateDto?,
    override val addresses: List<CreateAddressDto>?,
    override val emails: List<CreateEmailDto>?,
    override val patients: List<PatientDto.PatientDtoId>?,
) : CreateDoctor

interface CreateDoctor {
    val user: User
    val phones: List<CreatePhoneDto>?
    val title: String?
    val clinic: List<ClinicDto.ClinicDtoId>
    val dateOfBirth: LocalDateDto?
    val addresses: List<CreateAddressDto>?
    val emails: List<CreateEmailDto>?
    val patients: List<PatientDto.PatientDtoId>?

    sealed class User {
        data class NoUser(val nameInfo: CreateNameInfo) : User()
        data class Find(val userUid: String) : User()
        data class Create(
            val uid: String,
            val nameInfo: CreateNameInfo,
            val group: MediqGroupDto.MediqGroupId,
        ) : User()
    }
}

