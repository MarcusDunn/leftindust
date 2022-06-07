package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class EmailToEmailDtoConverter : InfallibleConverter<Email, EmailDto> {
    override fun convert(source: Email): EmailDto {
        return EmailDto(
            EmailDto.Id(source.id ?: throw NullEntityIdInConverterException(source)),
            source.type,
            source.email
        )
    }
}