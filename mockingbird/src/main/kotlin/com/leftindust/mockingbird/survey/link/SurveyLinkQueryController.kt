package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.link.SurveyLinkDto.SurveyLinkDtoId
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkQueryController(
    private val readSurveyLinkService: ReadSurveyLinkService,
    private val surveyLinkToSurveyLinkDtoConverter: InfallibleConverter<SurveyLink, SurveyLinkDto>,
) {
    @QueryMapping("surveyLinkById")
    suspend fun surveyLinkById(@Argument surveyLinkId: SurveyLinkDtoId): SurveyLinkDto? {
        val surveyLinkBySurveyLinkId = readSurveyLinkService.surveyLinkBySurveyLinkId(surveyLinkId)
            ?: return null
        return surveyLinkToSurveyLinkDtoConverter.convert(surveyLinkBySurveyLinkId)
    }
}