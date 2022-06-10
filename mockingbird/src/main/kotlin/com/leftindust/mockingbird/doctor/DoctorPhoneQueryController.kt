package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorPhoneQueryController(
    private val phoneService: ReadPhoneService,
    private val phoneToPhoneDtoConverter: InfallibleConverter<Phone, PhoneDto>,
) {
    @SchemaMapping
    suspend fun phones(doctorDto: DoctorDto): Flow<PhoneDto> {
        val phoneFlow = phoneService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadPhoneService::getByDoctorId)
        return phoneFlow.map { phoneToPhoneDtoConverter.convert(it) }
    }
}