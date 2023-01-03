package com.leftindust.mockingbird.survey.template

import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateSectionServiceImpl(
    private val surveyTemplateRepository: SurveyTemplateRepository,
) : ReadSurveyTemplateSectionService {
    override suspend fun surveyTemplateSectionServiceBySurveySectionId(surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId): List<SurveyTemplateSection>? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(surveyTemplateId.value)
            ?: return null
        return surveyTemplateEntity.sections
            .sortedBy { it.index }
            .map { it.toSurveyTemplateSection().onFailure { throw it.reason.toMockingbirdException() } }
    }
}