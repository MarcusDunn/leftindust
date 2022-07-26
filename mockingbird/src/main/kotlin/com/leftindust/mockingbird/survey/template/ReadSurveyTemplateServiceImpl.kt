package com.leftindust.mockingbird.survey.template


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateServiceImpl(
    val surveyTemplateRepository: SurveyTemplateRepository,
    val readPatientService: ReadPatientService,
    val surveyTemplateEntityToSurveyTemplateConverter: InfallibleConverter<SurveyTemplateEntity, SurveyTemplate>
) : ReadSurveyTemplateService {
    override suspend fun getByTemplateSurveyId(templateSurveyId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyTemplate? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(templateSurveyId.value)
            ?: return null
        return surveyTemplateEntityToSurveyTemplateConverter.convert(surveyTemplateEntity)
    }
}