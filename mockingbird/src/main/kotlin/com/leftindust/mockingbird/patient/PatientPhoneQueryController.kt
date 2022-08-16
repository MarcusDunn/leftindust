package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientPhoneQueryController(
    private val readPhoneService: ReadPhoneService,
    private val phoneToPhoneDtoConverter: InfallibleConverter<Phone, PhoneDto>,
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = PatientDto.GRAPHQL_PHONE_FIELD_NAME)
    suspend fun addresses(patientDto: PatientDto): List<PhoneDto> {
        val phones = readPhoneService.getByPatientId(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadPhoneService::getByPatientId)
        return phones.map { phoneToPhoneDtoConverter.convert(it) }
    }
}