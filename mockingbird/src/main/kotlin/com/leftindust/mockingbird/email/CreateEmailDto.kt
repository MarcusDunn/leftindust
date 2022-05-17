package com.leftindust.mockingbird.email

data class CreateEmailDto(
    override val type: EmailType,
    override val email: String
): CreateEmail