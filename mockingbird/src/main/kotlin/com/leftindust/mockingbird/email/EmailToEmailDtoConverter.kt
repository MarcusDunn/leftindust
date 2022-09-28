package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

@Component
class EmailToEmailDtoConverter : InfallibleConverter<EmailEntity, EmailDto> {
    override fun convert(source: EmailEntity): EmailDto {
        return EmailDto(
            EmailDto.EmailDtoId(source.id ?: throw NullEntityIdInConverterException(source)),
            source.type,
            source.address
        )
    }
}