package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {  }

@Component
class CreateContactDtoToCreateContactConverter(
    private val createEmailDtoToCreateEmailConverter: FallibleConverter<CreateEmailDto, CreateEmail>,
) : FallibleConverter<CreateContactDto, CreateContact> {
    override fun convert(source: CreateContactDto): CreateContact? {
        return CreateContactImpl(
            nameInfo = source.nameInfo,
            relationship = source.relationship,
            phones = source.phones,
            emails = source.emails.map { createEmailDtoToCreateEmailConverter.convert(it) ?: return null.also { logger.warn { FailedConversionMessage(source) } }},
        )
    }

    data class CreateContactImpl(
        override val nameInfo: CreateNameInfo,
        override val relationship: Relationship,
        override val phones: List<CreatePhone>,
        override val emails: List<CreateEmail>,
    ) : CreateContact

}