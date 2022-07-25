package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.SurveyTemplateSectionDto.SurveyTemplateSectionDtoId

interface ReadSurveyTemplateSectionInputService {
    suspend fun surveyTemplateSectionInputBySurveySection(surveyTemplateSectionDtoId: SurveyTemplateSectionDtoId): List<SurveyTemplateSectionInput>?
}
