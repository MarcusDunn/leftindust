package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.Email

@GraphQLName("Email")
data class GraphQLEmail(
    val type: GraphQLEmailType,
    val email: String,
) {
    constructor(email: Email) : this(email.type, email.email)
}
