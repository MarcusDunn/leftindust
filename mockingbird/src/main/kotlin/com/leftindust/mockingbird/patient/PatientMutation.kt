package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Controller

@Controller
class PatientMutation(
    private val updatePatientService: UpdatePatientService,
    private val createPatientService: CreatePatientService,
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>
) {

    suspend fun updatePatient(patient: UpdatePatientDto): PatientDto {
        TODO()
    }

    suspend fun addPatient(
        patient: CreatePatientDto,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): PatientDto {
        TODO()
    }
}