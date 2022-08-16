package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientEmailQueryController(
    private val readEmailService: ReadEmailService,
    private val emailToEmailDtoConverter: InfallibleConverter<Email, EmailDto>,
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = PatientDto.GRAPHQL_EMAIL_FIELD_NAME)
    suspend fun addresses(patientDto: PatientDto): List<EmailDto> {
        val emails = readEmailService.getPatientEmails(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadEmailService::getPatientEmails)
        return emails.map { emailToEmailDtoConverter.convert(it) }
    }
}