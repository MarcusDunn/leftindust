package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.validate.PhoneNumber

interface CreatePhone {
    val number: PhoneNumber
    val type: PhoneType
}