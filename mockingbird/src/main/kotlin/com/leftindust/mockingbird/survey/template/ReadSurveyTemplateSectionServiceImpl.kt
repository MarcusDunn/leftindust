package com.leftindust.mockingbird.survey.template

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Transactional
@Service
class ReadSurveyTemplateSectionServiceImpl : ReadSurveyTemplateSectionService {
    override suspend fun getSurveyTemplateSectionsBySurveyTemplateId(surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId): List<SurveyTemplateSection> {
        TODO("Not yet implemented")
    }
}