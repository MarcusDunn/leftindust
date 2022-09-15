package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.clinic.ClinicEntity
import com.leftindust.mockingbird.clinic.ClinicEntityToClinicConverter
import com.leftindust.mockingbird.util.AddressMother.DansHouse
import java.util.UUID

object ClinicMother {
    val clincEntityToClinicConverter = ClinicEntityToClinicConverter()

    object DansClinic {

        const val dansClinicName = "Dan's Clinic"
        val id = UUID.fromString("981c2f6d-5dfd-49a3-b7a1-bb0e01b93f20")
        val address = DansHouse.entityDetached
        val entityPersisted: ClinicEntity
            get() = ClinicEntity(
                name = dansClinicName,
                address = address,
                doctors = mutableSetOf(),
            ).apply { id = this@DansClinic.id }
        val entityUnpersisted: ClinicEntity
            get() = ClinicEntity(
            name = dansClinicName,
            address = address.apply { id = null },
            doctors = mutableSetOf()
        )

        val domain = clincEntityToClinicConverter.convert(entityPersisted.apply { name = dansClinicName })
    }
}