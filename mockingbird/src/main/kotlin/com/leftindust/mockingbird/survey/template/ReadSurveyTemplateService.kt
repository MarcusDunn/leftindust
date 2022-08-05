package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.survey.complete.CompleteSurveyDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_SURVEY_TEMPLATE')")
interface ReadSurveyTemplateService {
    suspend fun getByTemplateSurveyId(templateSurveyId: SurveyTemplateDto.SurveyTemplateDtoId): SurveyTemplate?
    suspend fun getByRange(range: Range): List<SurveyTemplate>
    suspend fun getByCompletedSurvey(completeSurveyId: CompleteSurveyDto.CompleteSurveyDtoId): SurveyTemplate?
}

