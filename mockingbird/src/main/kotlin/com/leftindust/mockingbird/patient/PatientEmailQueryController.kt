package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.email.toEmailDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientEmailQueryController(
    private val readEmailService: ReadEmailService,
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "emails")
    suspend fun addresses(patientDto: PatientDto): List<EmailDto> {
        val emails = readEmailService.getPatientEmails(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadEmailService::getPatientEmails)
        return emails.map { it.toEmailDto().onFailure { throw it.reason.toMockingbirdException() }}
    }
}