package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import java.time.LocalDate
import java.util.*

interface Patient {
    val id: UUID
    val thumbnail: ByteArray?
    val sex: Sex
    val dateOfBirth: LocalDate
    val gender: String?
    val ethnicity: Ethnicity?
    val insuranceNumber: String?
}
