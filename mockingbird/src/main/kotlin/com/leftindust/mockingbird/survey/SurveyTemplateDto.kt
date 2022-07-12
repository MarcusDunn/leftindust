package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.UUID

data class SurveyTemplateDto(
    override val id: Id
) : AbstractGraphQLDto<SurveyTemplateDto.Id>() {
    data class Id(override val value: UUID) : GraphQLID<UUID>
}