package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.UpdateAddress
import com.leftindust.mockingbird.patient.UpdateAddressDto

interface ClinicEdit {
    val cid: ClinicDto.ClinicDtoId
    val name: Updatable<String>
    val address: Updatable<UpdateAddress>
    val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
}