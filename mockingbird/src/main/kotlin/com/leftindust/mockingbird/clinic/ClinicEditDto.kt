package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.UpdateAddressDto

data class ClinicEditDto(
    override val cid: ClinicDto.ClinicDtoId,
    override val name: Updatable<String>,
    override val address: Updatable<UpdateAddressDto>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
) : ClinicEdit