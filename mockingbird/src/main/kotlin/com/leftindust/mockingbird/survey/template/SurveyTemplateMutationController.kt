package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.InfallibleConverter
import dev.forkhandles.result4k.onFailure
import graphql.GraphQLException
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateMutationController(
    private val createSurveyTemplateService: CreateSurveyTemplateService,
    private val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto>,
) {
    @MutationMapping
    suspend fun addSurveyTemplate(@Argument("surveyTemplate") createSurveyTemplateDto: CreateSurveyTemplateDto): SurveyTemplateDto {
        val createSurveyTemplate =
            createSurveyTemplateDto.toCreateSurveyTemplate().onFailure { throw it.reason.toMockingbirdException() }
                ?: throw GraphQLException("invalid SurveyTemplate: $createSurveyTemplateDto")
        val surveyTemplate = createSurveyTemplateService.createSurveyTemplate(createSurveyTemplate)
        return surveyTemplateToSurveyTemplateDtoConverter.convert(surveyTemplate)
    }
}