package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.InfallibleConverter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller

@Controller
class DoctorMutation(
    private val updateDoctorService: UpdateDoctorService,
    private val createDoctorService: CreateDoctorService,
    private val doctorDtoMapper: InfallibleConverter<Doctor, DoctorDto>,
) {
    private val logger = LoggerFactory.getLogger(DoctorMutation::class.java)

    suspend fun addDoctor(doctor: CreateDoctorDto): DoctorDto {
        val newDoctor = createDoctorService.addDoctor(doctor)
        return doctorDtoMapper.convert(newDoctor)
    }

    suspend fun editDoctor(doctor: UpdateDoctorDto): DoctorDto? {
        return updateDoctorService.editDoctor(doctor)?.let { doctorDtoMapper.convert(it) } ?: run {
            logger.warn(LogMessage("Returning null from ${DoctorMutation::editDoctor.name}", "${UpdateDoctorService::class.simpleName}.${UpdateDoctorService::editDoctor.name} returned null").toString())
            null
        }
    }
}