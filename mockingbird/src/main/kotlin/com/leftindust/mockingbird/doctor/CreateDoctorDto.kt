package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.user.MediqGroupDto
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

data class CreateDoctorUserDto(
    val discriminant: CreateDoctorUserDtoType,
    val userUid: String?,
    val uid: String?,
    val nameInfo: CreateNameInfo?,
    val group: MediqGroupDto.MediqGroupId?,
) {
    enum class CreateDoctorUserDtoType {
        NoUser,
        Find,
        Create,
    }
}

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
            val uid: String,
            val nameInfo: CreateNameInfo,
            val group: MediqGroupDto.MediqGroupId,
        ) : User()
    }
}

