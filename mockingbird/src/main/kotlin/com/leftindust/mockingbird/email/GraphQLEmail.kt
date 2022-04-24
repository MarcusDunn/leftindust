package com.leftindust.mockingbird.email

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("Email")
data class GraphQLEmail(
    val type: GraphQLEmailType,
    val email: String,
) {
    constructor(email: Email) : this(email.type, email.email)
}
