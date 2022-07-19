package com.leftindust.mockingbird.survey


import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateServiceImpl(
    val surveyTemplateRepository: SurveyTemplateRepository,
    val readPatientService: ReadPatientService,
) : ReadSurveyTemplateService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByTemplateSurveyId(templateSurveyId: SurveyTemplateDto.Id): SurveyTemplate? {
        return surveyTemplateRepository.findByIdOrNull(templateSurveyId.value)
    }
}