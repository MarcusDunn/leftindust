package com.leftindust.mockingbird.address

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.country.GraphQLCountry

@GraphQLName("AddressInput")
data class GraphQLAddressInput(
    val addressType: GraphQLAddressType,
    val address: String,
    val city: String,
    val country: GraphQLCountry,
    val province: String,
    val postalCode: String,
)

