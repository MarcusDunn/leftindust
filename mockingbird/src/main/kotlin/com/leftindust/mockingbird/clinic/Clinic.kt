package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import java.util.UUID

interface Clinic {
    val id: UUID
    val name: String
    val address: Address
    val doctors: Set<ClinicDoctorEntity>
}