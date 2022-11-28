package com.leftindust.mockingbird.survey.complete

import java.util.UUID
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface CompleteSurveyRepository : PagingAndSortingRepository<CompleteSurveyEntity, UUID>, CrudRepository<CompleteSurveyEntity, UUID> {
    fun findBySurveyLinkId(surveyLink_id: UUID): CompleteSurveyEntity?
}
