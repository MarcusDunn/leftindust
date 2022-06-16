package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.stereotype.Service

@Service
class ReadUserServiceImpl : ReadUserService {
    override suspend fun findUserByUid(uid: String): MediqUser? {
        TODO("Not yet implemented")
    }

    override suspend fun getByUserUid(uid: String): MediqUser? {
        return null
    }

    override suspend fun getUsers(range: RangeDto): List<MediqUser> {
        TODO("Not yet implemented")
    }

    override suspend fun findByDoctorId(did: DoctorDto.DoctorDtoId): MediqUser? {
        TODO("Not yet implemented")
    }

    override suspend fun findPatientUser(pid: PatientDto.PatientDtoId): MediqUser? {
        TODO("Not yet implemented")
    }
}