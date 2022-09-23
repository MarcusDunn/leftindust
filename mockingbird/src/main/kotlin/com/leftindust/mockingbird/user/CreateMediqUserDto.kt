package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.CreateNameInfoDto

data class CreateMediqUserDto(
    val uid: MediqUserDto.MediqUserUniqueId,
    val nameInfo: CreateNameInfoDto,
    val group: MediqGroupDto.MediqGroupId?,
    val doctor: DoctorDto.DoctorDtoId?,
)