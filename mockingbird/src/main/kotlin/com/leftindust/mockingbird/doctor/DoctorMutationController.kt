package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleDtoException
import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorMutationController(
    private val updateDoctorService: UpdateDoctorService,
    private val createDoctorService: CreateDoctorService,
    private val doctorToDoctorDtoInfallibleConverter: InfallibleConverter<Doctor, DoctorDto>,
    private val createDoctorDtoToCreateDoctorConverter: FallibleConverter<CreateDoctorDto, CreateDoctor>,
) {

    @MutationMapping
    suspend fun addDoctor(@Argument("createDoctor") createDoctorDto: CreateDoctorDto): DoctorDto {
        val createDoctor = createDoctorDtoToCreateDoctorConverter.convert(createDoctorDto)
        val newDoctor = createDoctorService.addDoctor(createDoctor ?: throw InconvertibleDtoException<CreateDoctor>(createDoctorDto))
        return doctorToDoctorDtoInfallibleConverter.convert(newDoctor)
    }

    @MutationMapping
    suspend fun editDoctor(@Argument("editDoctor") doctor: UpdateDoctorDto): DoctorDto? {
        val updatedDoctor = updateDoctorService.editDoctor(doctor)
        return updatedDoctor?.let { doctorToDoctorDtoInfallibleConverter.convert(it) }
    }
}