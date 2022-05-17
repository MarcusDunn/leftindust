package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.util.*

data class MediqGroupDto(
    override val id: MediqGroupId,
    val name: String,
) : AbstractGraphQLDto<MediqGroupDto.MediqGroupId>() {
    data class MediqGroupId(override val value: UUID) : GraphQLID<UUID>
}