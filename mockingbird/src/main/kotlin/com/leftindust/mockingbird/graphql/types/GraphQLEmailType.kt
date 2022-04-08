package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("EmailType")
enum class GraphQLEmailType {
    Work,
    Personal,
    School,
    Other,
}
