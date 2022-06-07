package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY')")
interface ReadSurveyService {
    suspend fun getBySurveyId(surveyId: SurveyDto.SurveyDtoId): Survey?
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Survey>?
}