package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class EmailToEmailDtoConverter : InfallibleConverter<Email, EmailDto> {
    override fun convert(source: Email): EmailDto {
        return EmailDto(
            EmailDto.EmailDtoId(source.id),
            source.type,
            source.address.value
        )
    }
}