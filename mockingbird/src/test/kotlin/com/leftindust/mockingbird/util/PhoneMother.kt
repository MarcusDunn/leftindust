package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneType

object PhoneMother {
    private val `jenny's home phone type` = PhoneType.Home
    private const val `jenny's home phone number` = "(604) 073-4427"
    val jennysHomePhone = Phone(
        number = `jenny's home phone number`,
        type = `jenny's home phone type`
    )

    private const val `jenny's work phone number` = "(604) 532-4327"
    private val `jenny's work phone type` = PhoneType.Work
    val jennysWorkPhone = Phone(
        number = `jenny's work phone number`,
        type = `jenny's work phone type`
    )
}