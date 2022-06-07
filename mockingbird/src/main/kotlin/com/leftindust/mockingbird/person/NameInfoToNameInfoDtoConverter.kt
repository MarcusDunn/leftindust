package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.NameInfoDto
import org.springframework.stereotype.Component

@Component
class NameInfoToNameInfoDtoConverter : InfallibleConverter<NameInfo, NameInfoDto> {
    override fun convert(source: NameInfo): NameInfoDto {
        return NameInfoDto(
            NameInfoDto.NameInfoDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            source.firstName,
            source.middleName,
            source.lastName
        )
    }
}