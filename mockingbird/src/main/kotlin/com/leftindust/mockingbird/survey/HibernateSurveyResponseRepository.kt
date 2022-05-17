package com.leftindust.mockingbird.survey

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface HibernateSurveyResponseRepository : CrudRepository<SurveyResponse, UUID> {
}