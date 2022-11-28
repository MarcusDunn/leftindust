package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
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

    override suspend fun getBySurveyLink(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): CompleteSurvey? {
        val completeSurveyEntity = completeSurveyRepository.findBySurveyLinkId(surveyLinkDtoId.value)
            ?: return null
        return completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity)
    }

    override suspend fun getMany(range: Range): List<CompleteSurvey> {
        return completeSurveyRepository
            .findAll(range.toPageable(Sort.by(CompleteSurveyEntity_.ID)))
            .map { completeSurveyEntityToCompleteSurvey.convert(it) }
            .toList()
    }
}