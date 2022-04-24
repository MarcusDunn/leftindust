package com.leftindust.mockingbird.phone

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("Phone")
data class GraphQLPhone(
    val number: String,
    val type: GraphQLPhoneType,
) {
    constructor(phone: Phone) : this(phone.number, phone.type)
}