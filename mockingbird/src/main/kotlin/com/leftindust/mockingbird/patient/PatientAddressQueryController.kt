package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import com.leftindust.mockingbird.address.toAddressDto
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientAddressQueryController(
    private val readAddressService: ReadAddressService,
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "addresses")
    suspend fun addresses(patientDto: PatientDto): List<AddressDto> {
        val addresses = readAddressService.getByPatientId(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadAddressService::getByPatientId)
        return addresses.map { it.toAddressDto() }
    }
}