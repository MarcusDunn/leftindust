package com.leftindust.mockingbird.person

fun CreateNameInfoDto.toCreateNameInfo(): CreateNameInfo {
    return CreateNameInfoImpl(
        firstName = firstName,
        middleName = middleName,
        lastName = lastName,
    )
}

data class CreateNameInfoImpl(
    override val firstName: String,
    override val middleName: String?,
    override val lastName: String
) : CreateNameInfo