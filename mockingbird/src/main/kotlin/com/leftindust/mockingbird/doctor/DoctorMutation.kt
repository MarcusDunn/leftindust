package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorMutation(
    private val updateDoctorService: UpdateDoctorService,
    private val createDoctorService: CreateDoctorService,
    private val doctorDtoMapper: InfallibleConverter<Doctor, DoctorDto>,
) {

    @MutationMapping
    suspend fun addDoctor(doctor: CreateDoctorDto): DoctorDto {
        val newDoctor = createDoctorService.addDoctor(doctor)
        return doctorDtoMapper.convert(newDoctor)
    }

    @MutationMapping
    suspend fun editDoctor(doctor: UpdateDoctorDto): DoctorDto? {
        val updatedDoctor = updateDoctorService.editDoctor(doctor)
        return updatedDoctor?.let { doctorDtoMapper.convert(it) }
    }
}