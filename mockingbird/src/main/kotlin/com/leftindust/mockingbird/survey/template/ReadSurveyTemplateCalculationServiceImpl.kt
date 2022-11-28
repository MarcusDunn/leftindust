package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateCalculationServiceImpl(
    private val surveyTemplateCalculationEntityToSurveyTemplateCalculationConverter: InfallibleConverter<SurveyTemplateCalculationEntity, SurveyTemplateCalculation>,
    private val surveyTemplateRepository: SurveyTemplateRepository
) : ReadSurveyTemplateCalculationService {
    override fun surveyTemplateCalculationBySurveyTemplateId(surveyTemplateDtoId: SurveyTemplateDto.SurveyTemplateDtoId): List<SurveyTemplateCalculation>? {
        val surveyTemplateEntity = surveyTemplateRepository.findByIdOrNull(surveyTemplateDtoId.value)
            ?: return null
        return surveyTemplateEntity.calculations.map { surveyTemplateCalculationEntityToSurveyTemplateCalculationConverter.convert(it) }
    }
}

