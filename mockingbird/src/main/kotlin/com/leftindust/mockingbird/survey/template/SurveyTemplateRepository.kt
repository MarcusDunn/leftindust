package com.leftindust.mockingbird.survey.template

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SurveyTemplateRepository : JpaRepository<SurveyTemplateEntity, UUID> {}
