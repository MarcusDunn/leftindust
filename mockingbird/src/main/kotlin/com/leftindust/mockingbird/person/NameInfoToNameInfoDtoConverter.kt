package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class NameInfoToNameInfoDtoConverter : InfallibleConverter<NameInfo, NameInfoDto> {
    override fun convert(source: NameInfo): NameInfoDto {
        return NameInfoDto(
            NameInfoDto.NameInfoDtoId(source.id ?: throw Exception("Expected non-null NameInfo id")),
            source.firstName,
            source.middleName,
            source.lastName
        )
    }
}