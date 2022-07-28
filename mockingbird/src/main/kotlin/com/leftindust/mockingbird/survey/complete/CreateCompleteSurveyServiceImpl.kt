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
        val newCompleteSurvey = CompleteSurveyEntity()
        val completeSurveyEntity = completeSurveyRepository.save(newCompleteSurvey)
        return completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity)
    }
}