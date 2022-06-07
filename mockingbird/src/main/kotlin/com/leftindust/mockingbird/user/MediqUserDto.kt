package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto

data class MediqUserDto(
    override val id: MediqUserUniqueId,
    val group: MediqGroupDto? = null,
) : AbstractGraphQLDto<MediqUserDto.MediqUserUniqueId>() {
    data class MediqUserUniqueId(override val value: String) : GraphQLID<String>
}
