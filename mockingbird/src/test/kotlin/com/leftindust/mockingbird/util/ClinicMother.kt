package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.clinic.Clinic
import com.leftindust.mockingbird.util.AddressMother.DansHouse
import java.util.UUID

object ClinicMother {
    object DansClinic {

        const val dansClinicName = "Dan's Clinic"
        val id = UUID.fromString("981c2f6d-5dfd-49a3-b7a1-bb0e01b93f20")
        val address = DansHouse.entityPersisted
        val entityPersisted: Clinic
            get() = Clinic(
                name = dansClinicName,
                address = address
            ).apply { id = this@DansClinic.id }
        val entityUnpersisted: Clinic
            get() = Clinic(
            name = dansClinicName,
            address = address.apply { id = null }
        )
    }

}