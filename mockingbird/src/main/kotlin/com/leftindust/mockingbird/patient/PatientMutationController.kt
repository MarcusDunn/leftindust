package com.leftindust.mockingbird.patient


import com.leftindust.mockingbird.InfallibleConverter
import dev.forkhandles.result4k.onFailure
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class PatientMutationController(
    private val patientToPatientDtoConverter: InfallibleConverter<Patient?, PatientDto>,
    private val createPatientService: CreatePatientService,
    private val updatePatientService: UpdatePatientService,
) {
    @MutationMapping("editPatient")
    suspend fun editPatient(@Argument("createPatient") editPatient: UpdatePatientDto): PatientDto {
        @Suppress("UNCHECKED_CAST")
        val patient = MapDelegatingUpdatePatientDto(editPatient.asMap(), editPatient)
        val updatedPatient = updatePatientService.update(patient)
        return patientToPatientDtoConverter.convert(updatedPatient)
    }

    @MutationMapping("addPatient")
    suspend fun addPatient(
        @Argument("createPatient") createPatientDto: CreatePatientDto,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): PatientDto {
        val createPatient = createPatientDto.toCreatePatient().onFailure { throw it.reason.toMockingbirdException() }
        val newPatient = createPatientService.addNewPatient(createPatient)
        return patientToPatientDtoConverter.convert(newPatient)
    }
}