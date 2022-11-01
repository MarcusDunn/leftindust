package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.toMap
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
        val createDoctor = createDoctorDto.toCreateDoctor().onFailure { throw it.reason.toMockingbirdException() }
        val newDoctor = createDoctorService.addDoctor(createDoctor)
        return doctorToDoctorDtoInfallibleConverter.convert(newDoctor)
    }

    @MutationMapping
    suspend fun editDoctor(@Argument("editDoctor") editDoctor: UpdateDoctorGraphQlDto): DoctorDto? {
        val updateDoctor = MapDelegatingUpdateDoctorDto(toMap(editDoctor)).toUpdateDoctor()
            .onFailure { throw it.reason.toMockingbirdException() }
        val updatedDoctor = updateDoctorService.editDoctor(updateDoctor)
            .onFailure { throw it.reason.toMockingbirdException() }
        return doctorToDoctorDtoInfallibleConverter.convert(updatedDoctor)
    }
}