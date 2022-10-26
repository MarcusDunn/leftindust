package com.leftindust.mockingbird.person

fun NameInfo.toNameInfoDto(): NameInfoDto {
    return NameInfoDto(
        NameInfoDto.NameInfoDtoId(id ?: throw Exception("Expected non-null NameInfo id")),
        firstName,
        middleName,
        lastName
    )
}