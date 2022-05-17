package com.leftindust.mockingbird.country

interface Province {
    fun asStrings(): List<String>
    fun asShortStrings(): List<String>
    fun longToShort(province: String): String

    fun shortToLong(province: String): String
}