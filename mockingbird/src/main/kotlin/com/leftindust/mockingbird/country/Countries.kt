package com.leftindust.mockingbird.country


enum class Countries {
    Canada;

    fun associatedStates(): Province {
        return when (this) {
            Canada -> CanadianProvince
        }
    }

    fun provinceLongToShort(province: String): String {
        return associatedStates().longToShort(province)
    }

    fun provinceShortToLong(province: String): String {
        return associatedStates().longToShort(province)
    }
}
