package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.types.input.toMap
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorMutationController(
    private val updateDoctorService: UpdateDoctorService,
    private val createDoctorService: CreateDoctorService,
) {

    @MutationMapping
    suspend fun addDoctor(@Argument("createDoctor") createDoctorDto: CreateDoctorDto): DoctorDto {
        val createDoctor = createDoctorDto.toCreateDoctor().onFailure { throw it.reason.toMockingbirdException() }
        val newDoctor = createDoctorService.addDoctor(createDoctor)
        return newDoctor.toDoctorDto().onFailure { throw it.reason.toMockingbirdException() }
    }

    @MutationMapping
    suspend fun editDoctor(@Argument("editDoctor") editDoctor: UpdateDoctorGraphQlDto): DoctorDto? {
        val updateDoctor = MapDelegatingUpdateDoctorDto(toMap(editDoctor)).toUpdateDoctor()
            .onFailure { throw it.reason.toMockingbirdException() }
        val updatedDoctor = updateDoctorService.editDoctor(updateDoctor)
            .onFailure { throw it.reason.toMockingbirdException() }
        return updatedDoctor.toDoctorDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}