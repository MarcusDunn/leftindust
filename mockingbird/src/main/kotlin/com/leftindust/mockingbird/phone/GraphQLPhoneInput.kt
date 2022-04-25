package com.leftindust.mockingbird.phone

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("PhoneInput")
data class GraphQLPhoneInput(
    val number: String,
    val type: GraphQLPhoneType
)