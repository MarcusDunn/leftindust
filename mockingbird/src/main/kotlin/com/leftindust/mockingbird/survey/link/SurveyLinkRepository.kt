package com.leftindust.mockingbird.survey.link

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface SurveyLinkRepository : CrudRepository<SurveyLinkEntity, UUID>