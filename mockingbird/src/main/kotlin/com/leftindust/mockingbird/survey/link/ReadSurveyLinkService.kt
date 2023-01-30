package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_LINK')")
interface ReadSurveyLinkService {
    suspend fun getBySurveyLinkId(surveyLinkId: SurveyLinkDto.SurveyLinkDtoId): SurveyLink?
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<SurveyLink>?
}
