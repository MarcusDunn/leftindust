package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleEntityException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadSurveyTemplateSectionInputServiceImpl(
    private val surveyTemplateSectionRepository: SurveyTemplateSectionRepository,
    private val surveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter: FallibleConverter<SurveyTemplateSectionInputEntity, SurveyTemplateSectionInput>,
) : ReadSurveyTemplateSectionInputService {
    override suspend fun surveyTemplateSectionInputBySurveySection(surveyTemplateSectionDtoId: SurveyTemplateSectionDto.SurveyTemplateSectionDtoId): List<SurveyTemplateSectionInput>? {
        val surveyTemplateSectionEntity = surveyTemplateSectionRepository.findByIdOrNull(surveyTemplateSectionDtoId.value)
            ?: return null
        return surveyTemplateSectionEntity.inputs
            .map { surveyTemplateSectionInputEntityToSurveyTemplateSectionInputConverter.convert(it) ?: throw InconvertibleEntityException(it, SurveyTemplateSectionInput::class) }
    }
}