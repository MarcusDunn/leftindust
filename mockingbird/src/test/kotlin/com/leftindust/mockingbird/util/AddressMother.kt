package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.AddressEntity
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.address.toAddress
import com.leftindust.mockingbird.address.toAddressDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.country.CountryState
import java.util.UUID


object AddressMother {
    object DansHouse {
        val addressType = AddressType.Home
        const val address = "842 Main Street"
        const val city = "South Vancouver"
        val country = Countries.Canada
        const val province = "BC"
        const val postalCode = "H8L210"
        val id = UUID.fromString("670ec435-682a-4563-b612-59a36e46f4a8")
        val entityDetached: AddressEntity
            get() = AddressEntity(
                type = addressType,
                address = address,
                city = city,
                countryState = CountryState(
                    country = country,
                    province = province
                ),
                postalCode = postalCode
            ).apply { id = this@DansHouse.id }

        val entityTransient: AddressEntity
            get() = AddressEntity(
                type = addressType,
                address = address,
                city = city,
                countryState = CountryState(
                    country = country,
                    province = province
                ),
                postalCode = postalCode
            )

        val createDto = CreateAddressGraphQlDto(
            addressType = addressType,
            address = address,
            city = city,
            country = JennysHouse.country,
            postalCode = postalCode,
            province = JennysHouse.province
        )

        val domain = entityDetached.toAddress()

        val dto = domain.toAddressDto()
    }


    object JennysHouse {
        val id = UUID.fromString("b3f35c8c-fdb8-42de-a64d-20172f1130cc")
        val addressType = AddressType.Home
        const val address = "841 Main Street"
        const val city = "North Vancouver"
        val country = Countries.Canada
        const val province = "BC"
        const val postalCode = "H8L2O9"
        val entityPersisted = AddressEntity(
            type = addressType,
            address = address,
            city = city,
            countryState = CountryState(
                country = country,
                province = province
            ),
            postalCode = postalCode
        ).apply { id = this@JennysHouse.id }

        val createDto = CreateAddressGraphQlDto(
            addressType = addressType,
                address = address,
                city = city,
                country = country,
                postalCode = postalCode,
                province = province
            )

        val domain = entityPersisted.toAddress()

        val dto = domain.toAddressDto()
    }

}