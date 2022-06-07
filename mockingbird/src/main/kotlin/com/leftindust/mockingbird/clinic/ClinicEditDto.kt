package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable

data class ClinicEditDto(
    override val cid: ClinicDto.ClinicDtoId,
    override val name: Updatable<String>,
    override val address: Updatable<CreateAddressDto>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
) : ClinicEdit