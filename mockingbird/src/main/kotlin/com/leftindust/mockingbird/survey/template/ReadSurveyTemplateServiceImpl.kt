package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.Range
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ReadSurveyTemplateServiceImpl(
    private val surveyTemplateEntityRepository: SurveyTemplateEntityRepository,
    private val surveyTemplateEntityToSurveyTemplateConverter: InfallibleConverter<SurveyTemplateEntity, SurveyTemplate>,
) : ReadSurveyTemplateService {
    override suspend fun getSurveyTemplateBySurveyId(surveyTemplateId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyTemplate? {
        val surveyTemplateEntity = surveyTemplateEntityRepository.findByIdOrNull(surveyTemplateId.value)
        return if (surveyTemplateEntity != null) {
            surveyTemplateEntityToSurveyTemplateConverter.convert(surveyTemplateEntity)
        } else {
            null
        }
    }

    override suspend fun getSurveyTemplateByRange(range: Range): List<SurveyTemplate> {
        val surveyTemplateEntities = surveyTemplateEntityRepository.findAll(range.toPageable(Sort.by(SurveyTemplateEntity_.ID)))
        return surveyTemplateEntities.map { surveyTemplateEntityToSurveyTemplateConverter.convert(it) }.toList()
    }
}