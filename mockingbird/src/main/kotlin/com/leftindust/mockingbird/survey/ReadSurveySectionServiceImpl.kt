package com.leftindust.mockingbird.survey

import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveySectionServiceImpl(
    private val surveyRepository: HibernateSurveyRepository
) : ReadSurveySectionService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun getBySurveyId(surveyId: SurveyDto.SurveyDtoId): Set<SurveySection>? {
        val survey = surveyRepository.findById(surveyId.value).orElse(null)
            ?: return null
        return survey.sections
    }
}