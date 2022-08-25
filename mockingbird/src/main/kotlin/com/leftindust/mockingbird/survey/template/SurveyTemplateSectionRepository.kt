package com.leftindust.mockingbird.survey.template

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface SurveyTemplateSectionRepository : CrudRepository<SurveyTemplateSectionEntity, UUID>{

}
