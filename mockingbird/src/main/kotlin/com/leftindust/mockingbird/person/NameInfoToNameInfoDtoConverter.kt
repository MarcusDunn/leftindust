package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class NameInfoToNameInfoDtoConverter : InfallibleConverter<NameInfoEntity, NameInfoDto> {
    override fun convert(source: NameInfoEntity): NameInfoDto {
        return NameInfoDto(
            NameInfoDto.NameInfoDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            source.firstName,
            source.middleName,
            source.lastName
        )
    }
}