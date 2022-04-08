package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.NameInfo

@GraphQLName("NameInfo")
data class GraphQLNameInfo(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
) {
    constructor(nameInfo: NameInfo) : this(
        firstName = nameInfo.firstName,
        middleName = nameInfo.middleName,
        lastName = nameInfo.lastName,
    )
}
