package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.Phone

@GraphQLName("Phone")
data class GraphQLPhone(
    val number: String,
    val type: GraphQLPhoneType,
) {
    constructor(phone: Phone) : this(phone.number, phone.type)
}