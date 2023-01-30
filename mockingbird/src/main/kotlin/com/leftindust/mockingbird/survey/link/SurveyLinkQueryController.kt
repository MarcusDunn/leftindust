package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.survey.link.SurveyLinkDto.SurveyLinkDtoId
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkQueryController(
    private val readSurveyLinkService: ReadSurveyLinkService,
) {
    @QueryMapping("surveyLinkById")
    suspend fun surveyLinkById(@Argument surveyLinkId: SurveyLinkDtoId): SurveyLinkDto? {
        val surveyLinkBySurveyLinkId = readSurveyLinkService.getBySurveyLinkId(surveyLinkId)
            ?: return null
        return surveyLinkBySurveyLinkId.toSurveyLinkDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}