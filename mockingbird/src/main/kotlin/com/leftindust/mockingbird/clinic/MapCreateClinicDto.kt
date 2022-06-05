package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import java.util.UUID

class MapUpdateClinicDto(val map: Map<String, Any?>) : ClinicEdit {
    override val cid = runCatching {
        ClinicDto.ClinicDtoId((map["cid"]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("cid is required", it)
    }
    override val name: Updatable<String> by UpdatableMapDelegate(map)
    override val address: Updatable<CreateAddressDto> by UpdatableMapDelegate(map)
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>> by UpdatableMapDelegate(map)
}