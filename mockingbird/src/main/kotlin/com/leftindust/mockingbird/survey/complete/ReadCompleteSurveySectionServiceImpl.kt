package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveySectionServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
    private val completeSurveySectionEntityToCompleteSurveySectionConverter: InfallibleConverter<CompleteSurveySectionEntity, CompleteSurveySection>,
) : ReadCompleteSurveySectionService {
    override fun completeSurveySectionsByCompleteSurveyId(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): List<CompleteSurveySection>? {
        val completeSurveySectionEntity = completeSurveyRepository.findByIdOrNull(completeSurveyId.value)
            ?: return null
        val completeSurveySections = completeSurveySectionEntity.sections
        return completeSurveySections.map { completeSurveySectionEntityToCompleteSurveySectionConverter.convert(it) }
    }
}

