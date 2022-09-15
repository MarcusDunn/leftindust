package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import java.util.UUID

interface Clinic {
    val id: UUID
    val name: String
}