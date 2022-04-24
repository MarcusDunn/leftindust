package com.leftindust.mockingbird.address

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.country.GraphQLCountry

@GraphQLName("AddressEditInput")
data class GraphQLAddressEditInput(
    val addressType: GraphQLAddressType? = null,
    val address: String? = null,
    val city: String? = null,
    val country: GraphQLCountry? = null,
    val province: String? = null,
    val postalCode: String? = null,
)