package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY')")
interface ReadSurveyResponseService {
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<SurveyResponse>?
}

