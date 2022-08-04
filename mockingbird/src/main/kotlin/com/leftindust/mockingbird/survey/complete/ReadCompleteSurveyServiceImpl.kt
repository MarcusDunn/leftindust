package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveyServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
    private val completeSurveyEntityToCompleteSurvey: InfallibleConverter<CompleteSurveyEntity, CompleteSurvey>
) : ReadCompleteSurveyService {
    override suspend fun completeSurveyByCompleteSurveyId(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): CompleteSurvey? {
        val completeSurveyEntity = completeSurveyRepository.findByIdOrNull(completeSurveyId.value)
            ?: return null
        return completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity)
    }
}