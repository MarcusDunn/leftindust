package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.survey.template.SurveyTemplateRepository
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateCompleteSurveyServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val completeSurveyEntityToCompleteSurvey: CompleteSurveyEntityToCompleteSurvey,
) : CreateCompleteSurveyService {
    private val logger = KotlinLogging.logger {  }
    override suspend fun createCompleteSurvey(createCompleteSurvey: CreateCompleteSurvey): CompleteSurvey? {
        val newCompleteSurvey = CompleteSurveyEntity(
            sections = createCompleteSurvey.completeSurveyTemplateSections
                .map { createCompleteSurveySection ->
                    CompleteSurveySectionEntity(
                        inputs = createCompleteSurveySection.completedSurveyInputs
                            .map { createCompleteSurveyInput ->
                                CompleteSurveySectionInputEntity(
                                    value = createCompleteSurveyInput.value
                                )
                            }
                            .toSet()
                    )
                }
                .toSet(),
            surveyTemplate = surveyTemplateRepository.findByIdOrNull(createCompleteSurvey.surveyTemplateId.value)
                ?: return null.also { logger.debug { "Did not find a surveyTemplate with id [${createCompleteSurvey.surveyTemplateId.value}] while creating $createCompleteSurvey" } }
        )
        val completeSurveyEntity = completeSurveyRepository.save(newCompleteSurvey)
        return completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity)
    }
}