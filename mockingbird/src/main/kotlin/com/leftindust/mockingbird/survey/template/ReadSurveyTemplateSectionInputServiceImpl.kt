package com.leftindust.mockingbird.survey.template

import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateSectionInputServiceImpl(
    private val surveyTemplateSectionRepository: SurveyTemplateSectionRepository,
) : ReadSurveyTemplateSectionInputService {
    override suspend fun surveyTemplateSectionInputBySurveySection(surveyTemplateSectionDtoId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId): List<SurveyTemplateSectionInput>? {
        val surveyTemplateSectionEntity =
            surveyTemplateSectionRepository.findByIdOrNull(surveyTemplateSectionDtoId.value)
                ?: return null
        return surveyTemplateSectionEntity.inputs
            .map {
                it.toSurveyTemplateSectionInput().onFailure { throw it.reason.toMockingbirdException() }
            }
    }
}