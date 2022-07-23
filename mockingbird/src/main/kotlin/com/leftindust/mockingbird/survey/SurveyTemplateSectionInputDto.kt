package com.leftindust.mockingbird.survey

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.SurveyTemplateSectionInputDto.SurveyTemplateSectionInputDtoId
import java.util.UUID

data class SurveyTemplateSectionInputDto(
    override val id: SurveyTemplateSectionInputDtoId,
    // todo add more fields here
) : AbstractGraphQLDto<SurveyTemplateSectionInputDtoId>() {
    data class SurveyTemplateSectionInputDtoId(override val value: UUID) : GraphQLID<UUID>
}