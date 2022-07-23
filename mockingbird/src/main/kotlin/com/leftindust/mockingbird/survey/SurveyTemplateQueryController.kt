package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.survey.SurveyTemplateDto.SurveyTemplateDtoId
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SurveyTemplateQueryController {

    @QueryMapping("surveyTemplateById")
    fun surveyTemplateBySurveyTemplateId(@Argument surveyTemplateId: SurveyTemplateDtoId): SurveyTemplateDto {
        TODO()
    }
}