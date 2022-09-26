package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadAddressServiceImpl(
    val patientRepository: PatientRepository,
    val doctorRepository: DoctorRepository
) : ReadAddressService {
    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Address>? {
        val doctor = doctorRepository.findByIdOrNull(doctorId.value) ?: return null
        return doctor.addresses.sortedBy { it.id }
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<Address>? {
        val byPatientId = patientRepository.findByIdOrNull(patientId.value) ?: return null
        return byPatientId.addresses.sortedBy { it.id }
    }
}