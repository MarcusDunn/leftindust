package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorMutationController(
    private val updateDoctorService: UpdateDoctorService,
    private val createDoctorService: CreateDoctorService,
    private val doctorToDoctorDtoInfallibleConverter: InfallibleConverter<Doctor, DoctorDto>,
) {

    @MutationMapping
    suspend fun addDoctor(@Argument("createDoctor") createDoctorDto: CreateDoctorDto): DoctorDto {
        val createDoctor = createDoctorDto.toCreateDoctor()
        val newDoctor = createDoctorService.addDoctor(createDoctor.onFailure { throw it.reason.toException()})
        return doctorToDoctorDtoInfallibleConverter.convert(newDoctor)
    }

    @MutationMapping
    suspend fun editDoctor(@Argument("editDoctor") doctor: UpdateDoctorDto): DoctorDto? {
        val updatedDoctor = updateDoctorService.editDoctor(doctor)
        return updatedDoctor?.let { doctorToDoctorDtoInfallibleConverter.convert(it) }
    }
}