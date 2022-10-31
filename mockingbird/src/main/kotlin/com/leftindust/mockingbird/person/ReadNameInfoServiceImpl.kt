package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.user.MediqUserRepository
import com.leftindust.mockingbird.user.MediqUserDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ReadNameInfoServiceImpl(
    private val mediqUserRepository: MediqUserRepository,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository
    ) : ReadNameInfoService {
    override fun getByUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): NameInfo? {
        TODO("Not yet implemented")
    }
    override fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): NameInfo? {
        val patientEntity = patientRepository.findByIdOrNull(patientDtoId.value)
            ?: return null
        return patientEntity.nameInfoEntity.toNameInfo()
    }
    override fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): NameInfo? {
        val doctorEntity = doctorRepository.findByIdOrNull(doctorDtoId.value)
            ?: return null
        return doctorEntity.nameInfoEntity.toNameInfo()
    }
}