package com.leftindust.mockingbird.survey.complete

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateCompleteSurveyServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
    private val completeSurveyEntityToCompleteSurvey: CompleteSurveyEntityToCompleteSurvey,
) : CreateCompleteSurveyService {
    override suspend fun createCompleteSurvey(createCompleteSurvey: CreateCompleteSurvey): CompleteSurvey {
        val newCompleteSurvey = CompleteSurveyEntity(
            sections = createCompleteSurvey.completeSurveyTemplateSections
                .map {
                    CompleteSurveySectionEntity(
                        inputs = it.completedSurveyInputs.map { CompleteSurveySectionInputEntity() }.toSet()
                    )
                }
                .toSet()
        )
        val completeSurveyEntity = completeSurveyRepository.save(newCompleteSurvey)
        return completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity)
    }
}