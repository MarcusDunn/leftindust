package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.address.toCreateAddress
import com.leftindust.mockingbird.doctor.DoctorDto

interface CreateClinicDto {
    val name: String
    val address: CreateAddressDto
    val doctors: List<DoctorDto.DoctorDtoId>
}

fun CreateClinicDto.toCreateClinic(): CreateClinic {
    return CreateClinicImpl(
        name, address.toCreateAddress(), doctors
    )
}

data class CreateClinicImpl(
    override val name: String,
    override val address: CreateAddress,
    override val doctors: List<DoctorDto.DoctorDtoId>
) : CreateClinic {

}

data class CreateClinicGraphQlDto(
    override val name: String,
    override val address: CreateAddressGraphQlDto,
    override val doctors: List<DoctorDto.DoctorDtoId>
) : CreateClinicDto

