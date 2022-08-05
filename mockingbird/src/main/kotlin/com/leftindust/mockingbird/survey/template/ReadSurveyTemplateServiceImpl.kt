package com.leftindust.mockingbird.survey.template


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateServiceImpl(
    val surveyTemplateRepository: SurveyTemplateRepository,
    val readPatientService: ReadPatientService,
    val surveyTemplateEntityToSurveyTemplateConverter: InfallibleConverter<SurveyTemplateEntity, SurveyTemplate>
) : ReadSurveyTemplateService {
    override suspend fun getByTemplateSurveyId(templateSurveyId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyTemplate? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(templateSurveyId.value)
            ?: return null
        return surveyTemplateEntityToSurveyTemplateConverter.convert(surveyTemplateEntity)
    }

    override suspend fun getByRange(range: Range): List<SurveyTemplate> {
        val surveyTemplateEntities = surveyTemplateRepository.findAll(range.toPageable())
        return surveyTemplateEntities.map { surveyTemplateEntityToSurveyTemplateConverter.convert(it) }.toList()
    }
}