package com.leftindust.mockingbird.survey.link

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.link.SurveyLinkDto.SurveyLinkDtoId
import java.util.UUID

data class SurveyLinkDto(
    override val id: SurveyLinkDtoId,
) : AbstractGraphQLDto<SurveyLinkDtoId>() {
    companion object {
        const val GRAPHQL_TYPE = "SurveyLink"
    }

    data class SurveyLinkDtoId(override val value: UUID) : GraphQLID<UUID>
}