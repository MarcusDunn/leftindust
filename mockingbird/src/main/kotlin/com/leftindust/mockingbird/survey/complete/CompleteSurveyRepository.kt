package com.leftindust.mockingbird.survey.complete

import java.util.UUID
import org.springframework.data.repository.PagingAndSortingRepository

interface CompleteSurveyRepository : PagingAndSortingRepository<CompleteSurveyEntity, UUID> {
    fun findBySurveyLinkId(surveyLink_id: UUID): CompleteSurveyEntity?

    fun findByPatientId(patient_id: UUID): CompleteSurveyEntity?
}
