package com.leftindust.mockingbird.survey

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface SurveyTemplateSectionRepository : CrudRepository<SurveyTemplateSectionEntity, UUID>{

}
