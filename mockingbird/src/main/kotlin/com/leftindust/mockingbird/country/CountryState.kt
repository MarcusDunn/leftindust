package com.leftindust.mockingbird.country

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class CountryState(
    @Enumerated(EnumType.STRING)
    var country: Countries,
    province: String,
) {

    var province = province
        set(value) {
            field = country.provinceShortToLong(value)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CountryState

        if (country != other.country) return false
        if (province != other.province) return false

        return true
    }

    override fun hashCode(): Int {
        var result = country.hashCode()
        result = 31 * result + province.hashCode()
        return result
    }

    override fun toString(): String {
        return "CountryState(country=$country, province='$province')"
    }


}