package com.leftindust.mockingbird.person


data class CreateNameInfoDto(
    override val firstName: String,
    override val middleName: String?,
    override val lastName: String,
) : CreateNameInfo

