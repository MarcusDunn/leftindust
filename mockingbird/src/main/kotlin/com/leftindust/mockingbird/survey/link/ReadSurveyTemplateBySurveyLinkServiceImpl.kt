package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.template.SurveyTemplate
import com.leftindust.mockingbird.survey.template.toSurveyTemplate
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadSurveyTemplateBySurveyLinkServiceImpl(
    private val surveyLinkRepository: SurveyLinkRepository,
) : ReadSurveyTemplateBySurveyLinkService {

    override suspend fun getBySurveyLinkId(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): SurveyTemplate? {
        val surveyLinkEntity = surveyLinkRepository.findByIdOrNull(surveyLinkDtoId.value)
            ?: return null
        return (surveyLinkEntity.surveyTemplateEntity).toSurveyTemplate()
            .onFailure { throw it.reason.toMockingbirdException() }
    }
}