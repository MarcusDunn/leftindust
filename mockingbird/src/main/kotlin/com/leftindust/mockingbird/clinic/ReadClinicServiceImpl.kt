package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import dev.forkhandles.result4k.onFailure
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class ReadClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val doctorRepository: DoctorRepository
) : ReadClinicService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): List<Clinic>? {
        val doctor = doctorRepository.findByIdOrNull(doctorDtoId.value) ?: return null
        return doctor.clinics.map { it.clinic.toClinic().onFailure { throw it.reason.toMockingbirdException() } }
    }

    override suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): Clinic? {
        val clinicEntity = clinicRepository.findByIdOrNull(clinicDtoId.value) ?: return null
        return clinicEntity.toClinic().onFailure { throw it.reason.toMockingbirdException() }
    }
}