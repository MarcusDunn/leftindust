package com.leftindust.mockingbird.survey.complete

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface CompleteSurveyRepository : CrudRepository<CompleteSurveyEntity, UUID>{
    fun findBySurveyLink_Id(surveyLink_id: UUID): Set<CompleteSurveyEntity>
}
