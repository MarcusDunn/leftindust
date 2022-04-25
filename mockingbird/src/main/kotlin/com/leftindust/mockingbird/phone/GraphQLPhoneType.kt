package com.leftindust.mockingbird.phone

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("PhoneType")
enum class GraphQLPhoneType {
    Work,
    Cell,
    Home,
    Pager,
    Other,
}