package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.country.CountryState




object AddressMother {
    private val `dan's houses address type` = AddressType.Home
    private const val `dan's houses address` = "841 Main Street"
    private const val `dan's houses city` = "North Vancouver"
    private val `dan's houses country` = Countries.Canada
    private const val `dan's houses province` = "BC"
    private const val `dan's houses postalCode` = "H8L2O9"
    val createDansHouseDto =  CreateAddressDto(
        addressType = `dan's houses address type`,
        address = `dan's houses address`,
        city = `dan's houses city`,
        country = `dan's houses country`,
        province = `dan's houses province`,
        postalCode = `dan's houses postalCode`
    )
    val dansHouse = Address(
        type = `dan's houses address type`,
        address = `dan's houses address`,
        city = `dan's houses city`,
        countryState = CountryState(
            country = `dan's houses country`,
            province = `dan's houses province`
        ),
        postalCode = `dan's houses postalCode`
    )

    private val `jenny's houses address type` = AddressType.Home
    private const val `jenny's houses address` = "841 Main Street"
    private const val `jenny's houses city` = "North Vancouver"
    private val `jenny's houses country` = Countries.Canada
    private const val `jenny's houses province` = "BC"
    private const val `jenny's houses postal code` = "H8L2O9"
    val jennysHouse = Address(
        type = `jenny's houses address type`,
        address = `jenny's houses address`,
        city = `jenny's houses city`,
        countryState = CountryState(
            country = `jenny's houses country`,
            province = `jenny's houses province`
        ),
        postalCode = `jenny's houses postal code`
    )
}