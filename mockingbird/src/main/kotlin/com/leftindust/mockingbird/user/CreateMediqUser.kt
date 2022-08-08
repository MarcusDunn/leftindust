package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.CreateNameInfo

interface CreateMediqUser {
    val uid: MediqUserDto.MediqUserUniqueId
    val nameInfo: CreateNameInfo
    val group: MediqGroupDto.MediqGroupId?
    val doctor: DoctorDto.DoctorDtoId?
    val proofOfValidUser: ProofOfValidUser
}

