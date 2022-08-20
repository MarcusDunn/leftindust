package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.FallibleConverter

import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import com.leftindust.mockingbird.phone.PhoneType
import org.springframework.stereotype.Component

@Component
class CreatePhoneDtoToCreatePhoneConverter : FallibleConverter<CreatePhoneDto, CreatePhone> {
    override fun convert(source: CreatePhoneDto): CreatePhone {
        return CreatePhoneImpl(
            source.number,
            source.type
        )
    }

    data class CreatePhoneImpl(
        override val number: String,
        override val type: PhoneType
    ) : CreatePhone
}