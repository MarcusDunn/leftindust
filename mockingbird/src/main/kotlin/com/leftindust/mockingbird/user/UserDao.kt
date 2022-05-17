package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import kotlinx.coroutines.flow.Flow

interface ReadUserService {
    suspend fun findUserByUid(uid: String): MediqUser?
    suspend fun getByUserUid(uid: String): MediqUser?
    suspend fun getUsers(range: RangeDto): Flow<MediqUser>
    suspend fun findByDoctorId(did: DoctorDto.DoctorDtoId): MediqUser?
    suspend fun findPatientUser(pid: PatientDto.PatientDtoId): MediqUser?
}