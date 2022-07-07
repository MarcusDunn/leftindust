package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class SurveyTemplateDto(
    override val id: SurveyTemplateDtoId,
    val title: String,
    val subtitle: String?,
) : AbstractGraphQLDto<SurveyTemplateDto.SurveyTemplateDtoId>() {
    companion object {
        const val SCHEMA_TYPE_NAME = "SurveyTemplate"
    }
    data class SurveyTemplateDtoId(override val value: UUID) : GraphQLID<UUID>
}