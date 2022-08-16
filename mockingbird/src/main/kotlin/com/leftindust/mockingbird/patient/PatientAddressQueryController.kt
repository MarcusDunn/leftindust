package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientAddressQueryController(
    private val readAddressService: ReadAddressService,
    private val addressToAddressDtoConverter: InfallibleConverter<Address, AddressDto>,
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = PatientDto.GRAPHQL_ADDRESS_FIELD_NAME)
    suspend fun addresses(patientDto: PatientDto): List<AddressDto> {
        val addresses = readAddressService.getByPatientId(patientDto.id)
            ?: throw NullSubQueryException(patientDto, ReadAddressService::getByPatientId)
        return addresses.map { addressToAddressDtoConverter.convert(it) }
    }
}