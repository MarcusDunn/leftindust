package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplate
import com.leftindust.mockingbird.survey.template.SurveyTemplateEntity
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class ReadSurveyTemplateBySurveyLinkServiceImpl(
    private val surveyLinkRepository: SurveyLinkRepository,
    private val surveyTemplateEntityToSurveyTemplateInfallibleConverter: InfallibleConverter<SurveyTemplateEntity, SurveyTemplate>,
) : ReadSurveyTemplateBySurveyLinkService {

    override suspend fun getBySurveyLinkId(surveyLinkDtoId: SurveyLinkDto.SurveyLinkDtoId): SurveyTemplate? {
        val surveyLinkEntity = surveyLinkRepository.findByIdOrNull(surveyLinkDtoId.value)
            ?: return null
        return surveyTemplateEntityToSurveyTemplateInfallibleConverter.convert(surveyLinkEntity.surveyTemplateEntity)
    }
}