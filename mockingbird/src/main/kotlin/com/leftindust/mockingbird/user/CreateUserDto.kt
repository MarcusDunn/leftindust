package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.CreateNameInfo

data class CreateUserDto(
    val uid: String,
    val nameInfo: CreateNameInfo,
    val group: MediqGroupDto.MediqGroupId,
    val doctor: DoctorDto.DoctorDtoId?,
)