package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.Address

// see GraphQLAddressInput! keep changes consistent!

@GraphQLName("Address")
data class GraphQLAddress(
    val addressType: GraphQLAddressType? = null,
    val address: String,
    val city: String,
    val country: GraphQLCountry,
    val province: String,
    val postalCode: String,
) {
    constructor(address: Address) : this(
        addressType = address.type,
        address = address.address,
        city = address.city,
        country = address.countryState.country,
        province = address.countryState.province.let { address.countryState.country.provinceLongToShort(it) },
        postalCode = address.postalCode
    )
}
