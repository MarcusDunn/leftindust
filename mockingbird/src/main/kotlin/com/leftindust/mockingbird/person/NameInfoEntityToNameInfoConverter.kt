package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.NullEntityIdInConverterException
import java.util.*

fun NameInfoEntity.toNameInfo(): NameInfo {
    return NameInfoImpl(
        id = id ?: throw NullEntityIdInConverterException(this),
        firstName = firstName,
        lastName = lastName,
        middleName = middleName
    )
}

private data class NameInfoImpl(
    override val id: UUID?,
    override val firstName: String,
    override val lastName: String,
    override val middleName: String?
) : NameInfo