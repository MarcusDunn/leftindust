package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import com.leftindust.mockingbird.patient.UpdateAddressDto
import java.util.UUID

class MapDelegatingUpdateClinicDto(val map: Map<String, Any?>) : ClinicEdit {
    override val cid = runCatching {
        ClinicDto.ClinicDtoId((map[ClinicEdit::cid.name]!! as Map<*, *>)["value"]!! as UUID)
    }.getOrElse {
        throw IllegalArgumentException("cid is required", it)
    }
    override val name: Updatable<String> by UpdatableMapDelegate(map)
    override val address: Updatable<UpdateAddressDto> by UpdatableMapDelegate(map)
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>> by UpdatableMapDelegate(map)
}