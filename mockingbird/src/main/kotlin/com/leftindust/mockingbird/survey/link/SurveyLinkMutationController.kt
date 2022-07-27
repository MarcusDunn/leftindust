package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyLinkMutationController(
    private val createSurveyLinkService: CreateSurveyLinkService,
    private val surveyLinkToSurveyLinkDtoConverter: InfallibleConverter<SurveyLink, SurveyLinkDto>
) {
    @MutationMapping("createSurveyLink")
    suspend fun createSurveyLink(@Argument("surveyTemplateId") surveyTemplateDtoId: SurveyTemplateDtoId): SurveyLinkDto? {
        val surveyLink = createSurveyLinkService.createSurveyLinkFromSurveyTemplateId(surveyTemplateDtoId)
            ?: return null
        return surveyLinkToSurveyLinkDtoConverter.convert(surveyLink)
    }
}