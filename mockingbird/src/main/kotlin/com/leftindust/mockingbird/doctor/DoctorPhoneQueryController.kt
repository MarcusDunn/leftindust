package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.phone.toPhoneDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorPhoneQueryController(
    private val phoneService: ReadPhoneService,
) {
    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "phoneNumbers")
    suspend fun phones(doctorDto: DoctorDto): List<PhoneDto> {
        val phones = phoneService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadPhoneService::getByDoctorId)
        return phones.map { it.toPhoneDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}