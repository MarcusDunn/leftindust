package com.leftindust.mockingbird.survey.complete

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.survey.complete.CompleteSurveySectionInputDto.CompleteSurveySectionInputDtoId
import java.util.UUID

data class CompleteSurveySectionInputDto(override val id: CompleteSurveySectionInputDtoId): AbstractGraphQLDto<CompleteSurveySectionInputDtoId>() {
    data class CompleteSurveySectionInputDtoId(override val value: UUID): GraphQLID<UUID>
}
