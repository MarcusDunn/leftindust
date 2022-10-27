package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable

interface ClinicEdit {
    val cid: ClinicDto.ClinicDtoId
    val name: Updatable<String>
    val address: Updatable<CreateAddress>
    val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
}