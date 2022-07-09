package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InfallibleConverter
import graphql.GraphQLException
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateMutationController(
    private val createSurveyTemplateService: CreateSurveyTemplateService,
    private val surveyTemplateToSurveyTemplateDtoConverter: InfallibleConverter<SurveyTemplate, SurveyTemplateDto>,
    private val createSurveyTemplateDtoToCreateSurveyTemplateConverter: FallibleConverter<CreateSurveyTemplateDto, CreateSurveyTemplate>,
) {
    @MutationMapping
    suspend fun addSurveyTemplate(@Argument("surveyTemplate") createSurveyTemplateDto: CreateSurveyTemplateDto): SurveyTemplateDto {
        val createSurveyTemplate = createSurveyTemplateDtoToCreateSurveyTemplateConverter.convert(createSurveyTemplateDto)
            ?: throw GraphQLException("invalid SurveyTemplate: $createSurveyTemplateDto")
        val surveyTemplate = createSurveyTemplateService.createSurveyTemplate(createSurveyTemplate)
        return surveyTemplateToSurveyTemplateDtoConverter.convert(surveyTemplate)
    }
}

