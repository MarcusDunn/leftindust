package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto

data class CreateClinicDto(
    override val name: String,
    override val address: CreateAddressDto,
    override val doctors: List<DoctorDto.DoctorDtoId>,
) : CreateClinic

