package com.leftindust.mockingbird.survey.template

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.template.SurveyTemplateDto.SurveyTemplateDtoId
import java.util.UUID

data class SurveyTemplateDto(
    override val id: SurveyTemplateDtoId,
    val title: String,
    val subtitle: String?,
) : AbstractGraphQLDto<SurveyTemplateDtoId>() {
    companion object {
        const val GRAPHQL_TYPE_NAME = "SurveyTemplate"
    }
    data class SurveyTemplateDtoId(override val value: UUID) : GraphQLID<UUID>
}