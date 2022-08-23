package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import org.springframework.transaction.annotation.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service


@Service
@Transactional
class ReadClinicServiceImpl(
    private val clinicEntityToClinicConverter: FallibleConverter<ClinicEntity, Clinic>,
    private val clinicRepository: ClinicRepository,
    private val doctorService: ReadDoctorService,
) : ReadClinicService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): List<Clinic>? {
        val doctor = doctorService.getByDoctorId(doctorDtoId) ?: return null
        return doctor.clinics.map { it.clinic }
    }

    override suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): Clinic? {
        return clinicRepository.findById(clinicDtoId.value).orElse(null) ?: null.also {
            logger.trace("Returning null from getByClinicId. Could not find a clinic with id $clinicDtoId")
        }
    }
}