package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorPhoneQueryController(
    private val phoneService: ReadPhoneService,
    private val phoneToPhoneDtoConverter: InfallibleConverter<Phone, PhoneDto>,
) {
    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "phoneNumbers")
    suspend fun phones(doctorDto: DoctorDto): List<PhoneDto> {
        val phones = phoneService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadPhoneService::getByDoctorId)
        return phones.map { phoneToPhoneDtoConverter.convert(it) }
    }
}