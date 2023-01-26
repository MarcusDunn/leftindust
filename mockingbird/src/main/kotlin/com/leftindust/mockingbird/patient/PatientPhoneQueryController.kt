package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.phone.toPhoneDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientPhoneQueryController(
    private val readPhoneService: ReadPhoneService,
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "phoneNumbers")
    suspend fun addresses(patientDto: PatientDto): List<PhoneDto> {
        val phones = readPhoneService.getByPatientId(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadPhoneService::getByPatientId)
        return phones.map { it.toPhoneDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}