package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CreateSurveyTemplateServiceImpl(
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val surveyTemplateEntityToSurveyTemplateConverter: InfallibleConverter<SurveyTemplateEntity, SurveyTemplate>,
) : CreateSurveyTemplateService {
    override suspend fun createSurveyTemplate(surveyTemplate: CreateSurveyTemplate): SurveyTemplate {
        val newSurveyTemplate = SurveyTemplateEntity()
        val surveyTemplateEntity = surveyTemplateRepository.save(newSurveyTemplate)
        return surveyTemplateEntityToSurveyTemplateConverter.convert(surveyTemplateEntity)
    }
}