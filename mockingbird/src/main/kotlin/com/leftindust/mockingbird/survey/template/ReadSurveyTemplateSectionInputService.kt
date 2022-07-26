package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.survey.template.SurveyTemplateSectionDto.SurveyTemplateSectionDtoId

interface ReadSurveyTemplateSectionInputService {
    suspend fun surveyTemplateSectionInputBySurveySection(surveyTemplateSectionDtoId: SurveyTemplateSectionDtoId): List<SurveyTemplateSectionInput>?
}
