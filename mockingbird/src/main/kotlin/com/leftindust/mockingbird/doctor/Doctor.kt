package com.leftindust.mockingbird.doctor

import java.time.LocalDate
import java.util.UUID

interface Doctor {
    val id: UUID
    val thumbnail: ByteArray?
    val title: String?
    val dateOfBirth: LocalDate?
}
