package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.email.EmailEntity
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorEmailQueryController(
    private val readEmailService: ReadEmailService,
    private val emailToEmailDtoConverter: InfallibleConverter<EmailEntity, EmailDto>,
) {
    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "emails")
    suspend fun emails(doctorDto: DoctorDto): List<EmailDto> {
        val emails = readEmailService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadEmailService::getByDoctorId)
        return emails.map { emailToEmailDtoConverter.convert(it) }
    }
}