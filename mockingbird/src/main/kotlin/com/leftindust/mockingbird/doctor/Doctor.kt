package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.person.NameInfo
import java.time.LocalDate
import java.util.UUID

interface Doctor {
    val id: UUID
    val nameInfo: NameInfo
    val thumbnail: ByteArray?
    val title: String?
    val dateOfBirth: LocalDate?
}
