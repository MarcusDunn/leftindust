package com.leftindust.mockingbird.survey

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class DeleteSurveyServiceImpl(
    private val surveyRepository: HibernateSurveyRepository,
) : DeleteSurveyService {
    override suspend fun deleteById(form: SurveyDto.SurveyDtoId): Survey? {
        val survey = surveyRepository.findById(form.value).orElse(null)
            ?: return null
        surveyRepository.delete(survey)
        return survey
    }
}