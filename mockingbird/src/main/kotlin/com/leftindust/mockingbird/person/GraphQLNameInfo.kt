package com.leftindust.mockingbird.person

import com.expediagroup.graphql.generator.annotations.GraphQLName

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
