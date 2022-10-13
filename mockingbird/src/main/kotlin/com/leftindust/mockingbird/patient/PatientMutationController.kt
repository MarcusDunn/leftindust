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
    @MutationMapping
    suspend fun editPatient(@Argument("editPatient") patient: UpdatePatientDto): PatientDto {
        val convertedPatient = patient.toUpdatePatient()
        val updatedPatient = updatePatientService.update(convertedPatient.onFailure { throw it.reason.toException() })
        return patientToPatientDtoConverter.convert(updatedPatient)
    }

    @MutationMapping
    suspend fun addPatient(@Argument("createPatient") createPatientDto: CreatePatientDto, dataFetchingEnvironment: DataFetchingEnvironment): PatientDto {
        val createPatient = createPatientDto.toCreatePatient()
        val newPatient = createPatientService.addNewPatient(createPatient.onFailure { throw it.reason.toException() })
        return patientToPatientDtoConverter.convert(newPatient)
    }
}

