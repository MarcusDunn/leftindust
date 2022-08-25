package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReadMediqUserServiceImpl(
    private val mediqUserRepository: MediqUserRepository,
    private val readDoctorService: ReadDoctorService
) : ReadMediqUserService {

    override suspend fun getByUserUid(uid: String): MediqUser? {
        return mediqUserRepository.findByIdOrNull(uid)
    }

    override suspend fun findByDoctorId(did: DoctorDto.DoctorDtoId): MediqUser? {
        return readDoctorService.getByDoctorId(did)?.user
    }
}