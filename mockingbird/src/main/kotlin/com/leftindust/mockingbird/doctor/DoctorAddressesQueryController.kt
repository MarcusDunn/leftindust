package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorAddressesQueryController(
    private val readAddressService: ReadAddressService,
    private val addressToAddressDtoConverter: InfallibleConverter<Address, AddressDto>,
) {
    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "address")
    suspend fun addresses(doctorDto: DoctorDto): List<AddressDto> {
        val addresses = readAddressService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadAddressService::getByDoctorId)
        return addresses.map { addressToAddressDtoConverter.convert(it) }
    }
}