package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component
import java.util.*

@Component
class NameInfoEntityToNameInfoConverterImpl: InfallibleConverter<NameInfoEntity, NameInfo> {
    override fun convert(source: NameInfoEntity): NameInfo {
        return NameInfoImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            firstName = source.firstName,
            lastName = source.lastName,
            middleName = source.middleName
        )
    }

    private data class NameInfoImpl(
        override val id: UUID?,
        override val firstName: String,
        override val lastName: String,
        override val middleName: String?
    ) : NameInfo
}