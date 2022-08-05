package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.user.MediqGroupDto

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