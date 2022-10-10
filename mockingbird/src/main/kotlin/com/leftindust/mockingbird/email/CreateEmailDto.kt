package com.leftindust.mockingbird.email

data class CreateEmailDto(
    val type: EmailType,
    val email: String
)