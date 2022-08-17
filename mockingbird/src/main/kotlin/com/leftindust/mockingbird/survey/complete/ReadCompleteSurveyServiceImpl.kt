package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.link.SurveyLinkDto
import com.leftindust.mockingbird.survey.link.SurveyLinkRepository
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadCompleteSurveyServiceImpl(
    private val completeSurveyRepository: CompleteSurveyRepository,
    private val surveyLinkRepository: SurveyLinkRepository,
    private val completeSurveyEntityToCompleteSurvey: InfallibleConverter<CompleteSurveyEntity, CompleteSurvey>
) : ReadCompleteSurveyService {
    override suspend fun completeSurveyByCompleteSurveyId(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): CompleteSurvey? {
        val completeSurveyEntity = completeSurveyRepository.findByIdOrNull(completeSurveyId.value)
            ?: return null
        return completeSurveyEntityToCompleteSurvey.convert(completeSurveyEntity)
    }

    override suspend fun getBySurveyLink(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): List<CompleteSurvey>? {
        val completeSurveyEntities = completeSurveyRepository.findBySurveyLink_Id(surveyLinkDtoId.value)
        return completeSurveyEntities.map { completeSurveyEntityToCompleteSurvey.convert(it) }
    }
}