package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleDtoException
import com.leftindust.mockingbird.InfallibleConverter
import dev.forkhandles.result4k.get
import dev.forkhandles.result4k.onFailure
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class PatientMutationController(
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>,
    private val createPatientDtoToCreatePatient: FallibleConverter<CreatePatientDto, CreatePatient>,
    private val createPatientService: CreatePatientService,
    //private val updatePatientService: UpdatePatientService
) {

    suspend fun editPatient(@Argument("editPatient") patient: UpdatePatientDto): PatientDto {
        /*val updatedPatient = updatePatientService.update(patient)
        return patientToPatientDtoConverter.convert(updatedPatient)*/
        TODO("Update patient service not implemented")
    }

    @MutationMapping
    suspend fun addPatient(@Argument("createPatient") createPatientDto: CreatePatientDto, dataFetchingEnvironment: DataFetchingEnvironment): PatientDto {
        val createPatient = createPatientDtoToCreatePatient.convert(createPatientDto) ?: throw InconvertibleDtoException<CreatePatient>(createPatientDto)
        val newPatient = createPatientService.addNewPatient(createPatient).onFailure { throw it.reason.toMockingbirdException() }
        return patientToPatientDtoConverter.convert(newPatient)
    }
}