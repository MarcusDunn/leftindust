package com.leftindust.mockingbird.email

interface CreateEmailDto {
    val type: EmailType
    val email: String
}

data class CreateEmailGraphQlDto(
    override val type: EmailType,
    override val email: String
) : CreateEmailDto