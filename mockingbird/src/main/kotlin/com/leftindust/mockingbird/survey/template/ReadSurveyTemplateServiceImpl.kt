package com.leftindust.mockingbird.survey.template


import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveyRepository
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateServiceImpl(
    val surveyTemplateRepository: SurveyTemplateRepository,
    val completeSurveyRepository: CompleteSurveyRepository,
    val readPatientService: ReadPatientService,
) : ReadSurveyTemplateService {
    override suspend fun getByTemplateSurveyId(templateSurveyId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyTemplate? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(templateSurveyId.value)
            ?: return null
        return surveyTemplateEntity.toSurveyTemplate().onFailure { throw it.reason.toMockingbirdException() }
    }

    override suspend fun getByRange(range: Range): List<SurveyTemplate> {
        val surveyTemplateEntities = surveyTemplateRepository.findAll(range.toPageable())
        return surveyTemplateEntities.map {
            it.toSurveyTemplate().onFailure { throw it.reason.toMockingbirdException() }
        }.toList()
    }

    override suspend fun getByCompletedSurvey(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): SurveyTemplate? {
        val completeSurveyEntity = completeSurveyRepository.findByIdOrNull(completeSurveyId.value)
            ?: return null
        return (completeSurveyEntity.surveyLink.surveyTemplateEntity).toSurveyTemplate()
            .onFailure { throw it.reason.toMockingbirdException() }
    }
}