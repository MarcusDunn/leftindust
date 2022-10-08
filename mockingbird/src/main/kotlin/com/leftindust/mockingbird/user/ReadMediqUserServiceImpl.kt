package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReadMediqUserServiceImpl(
    private val mediqUserRepository: MediqUserRepository,
    private val doctorRepository: DoctorRepository
) : ReadMediqUserService {

    override suspend fun getByUserUid(uid: String): MediqUser? {
        return mediqUserRepository.findByIdOrNull(uid)
    }

    override suspend fun findByDoctorId(did: DoctorDto.DoctorDtoId): MediqUser? {
        return doctorRepository.findByIdOrNull(did.value)?.user
    }
}