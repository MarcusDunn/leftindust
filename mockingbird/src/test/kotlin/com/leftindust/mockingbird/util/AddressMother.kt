package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.country.CountryState
import java.util.UUID


object AddressMother {
    object DansHouse {
        val addressType = AddressType.Home
        const val address = "841 Main Street"
        const val city = "North Vancouver"
        val country = Countries.Canada
        const val province = "BC"
        const val postalCode = "H8L2O9"
        val id = UUID.fromString("670ec435-682a-4563-b612-59a36e46f4a8")
        val createDto =  CreateAddressDto(
            addressType = addressType,
            address = address,
            city = city,
            country = country,
            province = province,
            postalCode = postalCode
        )
        val entityPersisted = Address(
            type = addressType,
            address = address,
            city = city,
            countryState = CountryState(
                country = country,
                province = province
            ),
            postalCode = postalCode
        ).apply { id = this@DansHouse.id }
    }


    object JennysHouse {
        val id = UUID.fromString("b3f35c8c-fdb8-42de-a64d-20172f1130cc")
        val addressType = AddressType.Home
        const val address = "841 Main Street"
        const val city = "North Vancouver"
        val country = Countries.Canada
        const val province = "BC"
        const val postalCode = "H8L2O9"
        val entityPersisted = Address(
            type = addressType,
            address = address,
            city = city,
            countryState = CountryState(
                country = country,
                province = province
            ),
            postalCode = postalCode
        ).apply { id = this@JennysHouse.id }
    }

}