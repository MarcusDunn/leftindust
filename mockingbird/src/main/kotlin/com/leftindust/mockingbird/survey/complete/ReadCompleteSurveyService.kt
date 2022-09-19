package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto.CompleteSurveyDtoId
import com.leftindust.mockingbird.survey.link.SurveyLinkDto

interface ReadCompleteSurveyService {
    suspend fun completeSurveyByCompleteSurveyId(completeSurveyId: CompleteSurveyDtoId): CompleteSurvey?
    suspend fun getBySurveyLink(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): CompleteSurvey?
    suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): CompleteSurvey?
    suspend fun getMany(range: Range) : List<CompleteSurvey>
}
