package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.email.*
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorEmailQueryController(
    private val readEmailService: ReadEmailService,
) {
    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "emails")
    suspend fun emails(doctorDto: DoctorDto): List<EmailDto> {
        val emails = readEmailService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadEmailService::getByDoctorId)
        return emails.map { it.toEmailDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}