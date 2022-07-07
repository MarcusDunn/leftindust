package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class SurveyTemplateSectionDto(
    override val id: SurveyTemplateSectionDtoId,
    val title: String,
    val subtitle: String?,
): AbstractGraphQLDto<SurveyTemplateSectionDto.SurveyTemplateSectionDtoId>() {
    data class SurveyTemplateSectionDtoId(override val value: UUID): GraphQLID<UUID>
}

