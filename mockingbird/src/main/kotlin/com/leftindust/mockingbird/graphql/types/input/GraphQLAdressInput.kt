package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLAddressType
import com.leftindust.mockingbird.graphql.types.GraphQLCountry

@GraphQLName("AddressInput")
data class GraphQLAddressInput(
    val addressType: GraphQLAddressType,
    val address: String,
    val city: String,
    val country: GraphQLCountry,
    val province: String,
    val postalCode: String,
)

@GraphQLName("AddressEditInput")
data class GraphQLAddressEditInput(
    val addressType: GraphQLAddressType? = null,
    val address: String? = null,
    val city: String? = null,
    val country: GraphQLCountry? = null,
    val province: String? = null,
    val postalCode: String? = null,
)