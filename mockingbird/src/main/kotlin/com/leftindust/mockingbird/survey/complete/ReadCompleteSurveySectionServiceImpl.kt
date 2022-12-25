package com.leftindust.mockingbird.survey.complete

import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveySectionServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
) : ReadCompleteSurveySectionService {
    override fun completeSurveySectionsByCompleteSurveyId(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): List<CompleteSurveySection>? {
        val completeSurveySectionEntity = completeSurveyRepository.findByIdOrNull(completeSurveyId.value)
            ?: return null
        val completeSurveySections = completeSurveySectionEntity.sections
        return completeSurveySections.map {
            it.toCompleteSurveySection().onFailure { throw it.reason.toMockingbirdException() }
        }
    }
}

