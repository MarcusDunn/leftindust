package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.SurveyDto
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    suspend fun assignSurvey(patients: List<PatientDto.PatientDtoId>, survey: SurveyDto.SurveyDtoId): Flow<PatientDto> {
        return updatePatientService.assignForms(patients, survey).map { patientToPatientDtoConverter.convert(it) }
    }
}