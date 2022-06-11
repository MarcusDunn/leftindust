package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.clinic.Clinic
import com.leftindust.mockingbird.clinic.CreateClinicDto
import java.util.UUID

object ClinicMother {
    private const val dansClinicName = "Dan's Clinic"
    private val dansHousesUUID = UUID.fromString("981c2f6d-5dfd-49a3-b7a1-bb0e01b93f20")
    val dansClinicPersisted
        get() = dansClinicUnpersisted.apply { id = dansHousesUUID }
    val dansClinicUnpersisted
        get() = Clinic(dansClinicName, AddressMother.dansHouse)


    val createDansClinicDto
        get() = CreateClinicDto(dansClinicName, AddressMother.createDansHouseDto, emptyList())
}