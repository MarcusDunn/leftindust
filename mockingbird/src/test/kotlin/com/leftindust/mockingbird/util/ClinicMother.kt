package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.clinic.Clinic
import java.util.UUID

object ClinicMother {
    val dansClinic = Clinic("Dan's Clinic", AddressMother.dansHouse).apply { id = UUID.randomUUID() }
}