package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateRepository
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateSurveyLinkServiceImpl(
    private val surveyLinkRepository: SurveyLinkRepository,
    private val surveyTemplateRepository: SurveyTemplateRepository,
    private val surveyLinkEntityToSurveyLinkConverter: InfallibleConverter<SurveyLinkEntity, SurveyLink>,
) : CreateSurveyLinkService {
    override suspend fun createSurveyLinkFromSurveyTemplateId(surveyTemplateDtoId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyLink? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(surveyTemplateDtoId.value)
            ?: return null

        val newSurveyLinkEntity = SurveyLinkEntity(
            surveyTemplateEntity = surveyTemplateEntity
        )
        val surveyLinkEntity = surveyLinkRepository.save(newSurveyLinkEntity)
        return surveyLinkEntityToSurveyLinkConverter.convert(surveyLinkEntity)
    }
}

