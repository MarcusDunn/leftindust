package com.leftindust.mockingbird.address

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("AddressType")
enum class GraphQLAddressType {
    Home,
    Work,
    School,
    Apartment,
    Other;
}