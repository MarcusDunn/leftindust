package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLPhoneType

@GraphQLName("PhoneInput")
data class GraphQLPhoneInput(
    val number: String,
    val type: GraphQLPhoneType
)