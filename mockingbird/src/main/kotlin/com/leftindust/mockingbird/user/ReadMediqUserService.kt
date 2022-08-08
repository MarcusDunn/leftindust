package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto

interface ReadMediqUserService {
    suspend fun getByUserUid(uid: String): MediqUser?
    suspend fun findByDoctorId(did: DoctorDto.DoctorDtoId): MediqUser?
}