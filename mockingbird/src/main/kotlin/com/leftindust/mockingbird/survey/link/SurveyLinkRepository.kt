package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.complete.CompleteSurveyEntity
import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface SurveyLinkRepository : CrudRepository<SurveyLinkEntity, UUID> {
    fun findByPatientId(patient_id: UUID): SurveyLinkEntity?

}