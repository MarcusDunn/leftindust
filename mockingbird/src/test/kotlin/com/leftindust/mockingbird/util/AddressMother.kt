package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.country.CountryState

object AddressMother {
    val dansHouse = Address(AddressType.Home, "841 Main Street", "North Vancouver", CountryState(Countries.Canada, "BC"), "H8L2O9")
}