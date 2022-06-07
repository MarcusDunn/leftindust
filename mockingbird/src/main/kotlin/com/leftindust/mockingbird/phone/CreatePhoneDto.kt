package com.leftindust.mockingbird.phone

data class CreatePhoneDto(
    override val number: String,
    override val type: PhoneType,
) : CreatePhone

