package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.doctor.DoctorDto

interface CreateClinic {
    val name: String
    val address: CreateAddress
    val doctors: List<DoctorDto.DoctorDtoId>
}