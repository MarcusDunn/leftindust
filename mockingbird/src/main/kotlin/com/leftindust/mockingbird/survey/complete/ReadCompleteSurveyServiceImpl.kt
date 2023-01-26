package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveyServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
) : ReadCompleteSurveyService {
    override suspend fun completeSurveyByCompleteSurveyId(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): CompleteSurvey? {
        val completeSurveyEntity = completeSurveyRepository.findByIdOrNull(completeSurveyId.value)
            ?: return null
        return completeSurveyEntity.toCompleteSurvey().onFailure { throw it.reason.toMockingbirdException() }
    }

    override suspend fun getBySurveyLink(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): CompleteSurvey? {
        val completeSurveyEntity = completeSurveyRepository.findBySurveyLinkId(surveyLinkDtoId.value)
            ?: return null
        return completeSurveyEntity.toCompleteSurvey().onFailure { throw it.reason.toMockingbirdException() }
    }

    override suspend fun getMany(range: Range): List<CompleteSurvey> {
        return completeSurveyRepository
            .findAll(range.toPageable(Sort.by(CompleteSurveyEntity_.ID)))
            .map { it.toCompleteSurvey().onFailure { throw it.reason.toMockingbirdException() } }
            .toList()
    }
}
