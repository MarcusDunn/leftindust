package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import org.springframework.transaction.annotation.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
@Transactional
class ReadClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val doctorService: ReadDoctorService,
) : ReadClinicService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun getByDoctor(doctorDtoId: DoctorDto.DoctorDtoId): Flow<Clinic>? {
        val doctor = doctorService.getByDoctorId(doctorDtoId) ?: run {
            logger.trace("Returning null from getByDoctor. Could not find a doctor with id $doctorDtoId")
            return null
        }
        return doctor.clinics.map { it.clinic }.asFlow()
    }

    override suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): Clinic? {
        return clinicRepository.findById(clinicDtoId.value).orElse(null) ?: run {
            logger.trace("Returning null from getByClinicId. Could not find a clinic with id $clinicDtoId")
            null
        }
    }
}