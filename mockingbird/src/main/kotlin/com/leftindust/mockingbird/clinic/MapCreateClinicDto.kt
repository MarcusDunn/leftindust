package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import java.util.UUID

class MapDelegatingUpdateClinicDto(val map: Map<String, Any?>) : ClinicEditDto {
    override val cid = runCatching {
        ClinicDto.ClinicDtoId((map[ClinicEdit::cid.name]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("cid is required", it)
    }
    override val name: Updatable<String> by UpdatableMapDelegate(map)
    override val address: Updatable<MapDelegatingCreateAddressDto> by UpdatableMapDelegate(map)
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>> by UpdatableMapDelegate(map)
}

class MapDelegatingCreateAddressDto(val map: Map<String, Any?>) : CreateAddressDto {
    override val addressType: AddressType by map
    override val address: String by map
    override val city: String by map
    override val country: Countries by map
    override val province: String by map
    override val postalCode: String by map
}