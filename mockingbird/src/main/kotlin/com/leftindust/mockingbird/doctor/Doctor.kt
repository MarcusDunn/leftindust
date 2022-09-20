package com.leftindust.mockingbird.doctor

interface Doctor {
    val id: UUID
    val nameInfo: NameInfo
    val thumbnail: ByteArray?
    val title: String?
    val dateOfBirth: LocalDate?
}
