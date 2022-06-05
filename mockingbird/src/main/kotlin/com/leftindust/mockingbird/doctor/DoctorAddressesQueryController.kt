package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorAddressesQueryController(
    private val readAddressService: ReadAddressService,
    private val addressToAddressDtoConverter: InfallibleConverter<Address, AddressDto>,
) {
    @SchemaMapping
    suspend fun addresses(doctorDto: DoctorDto): Flow<AddressDto> {
        val addressFlow = readAddressService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadAddressService::getByDoctorId)
        return addressFlow.map { addressToAddressDtoConverter.convert(it) }
    }
}