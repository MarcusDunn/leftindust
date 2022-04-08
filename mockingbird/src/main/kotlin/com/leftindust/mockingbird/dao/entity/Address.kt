package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.GraphQLAddressType
import com.leftindust.mockingbird.graphql.types.input.GraphQLAddressEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLAddressInput
import javax.persistence.*

@Entity
class Address(
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var type: GraphQLAddressType?,
    @Column(nullable = false)
    var address: String, //todo validation
    @Column(nullable = false)
    var city: String,
    @Embedded
    var countryState: CountryState,
    @Column(nullable = false)
    var postalCode: String, //todo validation
) : AbstractJpaPersistable() {
    constructor(graphQLAddress: GraphQLAddressInput) : this(
        type = graphQLAddress.addressType,
        address = graphQLAddress.address,
        city = graphQLAddress.city,
        countryState = CountryState(
            country = graphQLAddress.country,
            province = graphQLAddress.province
        ),
        postalCode = graphQLAddress.postalCode,
    )

    fun setByGqlInput(gqlAddressInput: GraphQLAddressEditInput) {
        type = gqlAddressInput.addressType ?: type
        address = gqlAddressInput.address ?: address
        city = gqlAddressInput.city ?: city
        countryState = CountryState(
            country = gqlAddressInput.country ?: countryState.country,
            province = gqlAddressInput.province ?: countryState.province
        ) // we recreate it here to verify country/province constancy as it's checked in CountryState constructor
        postalCode = gqlAddressInput.postalCode ?: postalCode
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (type != other.type) return false
        if (address != other.address) return false
        if (city != other.city) return false
        if (countryState != other.countryState) return false
        if (postalCode != other.postalCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + address.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + countryState.hashCode()
        result = 31 * result + postalCode.hashCode()
        return result
    }

    override fun toString(): String {
        return "Address(type=$type, address='$address', city='$city', countryState=$countryState, postalCode='$postalCode')"
    }
}
