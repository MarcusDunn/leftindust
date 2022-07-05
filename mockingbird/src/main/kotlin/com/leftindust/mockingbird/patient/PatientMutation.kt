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

    suspend fun assignSurvey(patients: List<PatientDto.PatientDtoId>, survey: SurveyDto.SurveyDtoId): List<PatientDto> {
        return updatePatientService
            .assignForms(patients, survey)
            .map { patientToPatientDtoConverter.convert(it) }
    }
}