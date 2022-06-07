package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component

@Component
class ContactQuery(
    private val readContactService: ReadContactService,
    private val contactContactDtoConverter: InfallibleConverter<Contact, ContactDto>,
) {
    private val logger = LoggerFactory.getLogger(ContactQuery::class.java)

    @QueryMapping
    suspend fun getContactsByPatient(
        @Argument patientId: PatientDto.PatientDtoId,
    ): Flow<ContactDto>? {
        val contactFlow = readContactService.getByPatientId(patientId)
        return if (contactFlow == null) {
            logger.trace(LogMessage("Returning null for contacts of patient $patientId", "ReadContactService.getByPatientId returned null").toString())
            null
        } else {
            return contactFlow.map { contactContactDtoConverter.convert(it) }
        }
    }

    suspend fun phones(): List<PhoneDto> = TODO()

    suspend fun emails(): List<EmailDto> = TODO()
}