package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.address.toCreateAddress
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable

interface ClinicEditDto {
    val cid: ClinicDto.ClinicDtoId
    val name: Updatable<String>
    val address: Updatable<CreateAddressDto>
    val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
}


fun ClinicEditDto.toClinicEdit(): ClinicEdit {
    return ClinicEditImpl(cid, name, address.map { it.toCreateAddress() }, doctors)
}

data class ClinicEditImpl(
    override val cid: ClinicDto.ClinicDtoId,
    override val name: Updatable<String>,
    override val address: Updatable<CreateAddress>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
) : ClinicEdit

data class UpdateClinicGraphQlDtoDto(
    val cid: ClinicDto.ClinicDtoId,
    val name: String?,
    val address: CreateAddressGraphQlDto?,
    val doctors: List<DoctorDto.DoctorDtoId>
)
