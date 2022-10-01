package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneToPhoneDtoConverter
import com.leftindust.mockingbird.phone.PhoneType
import java.util.UUID

object PhoneMother {
    val phoneToPhoneDtoConverter = PhoneToPhoneDtoConverter()

    object JennysHomePhone {
        val id = UUID.fromString("548143f2-a3ba-4c95-ac3a-27e76c37149e")
        val type = PhoneType.Home
        const val number = "(604) 073-4427"
        val entityPersisted = Phone(
            number = number,
            type = type
        ).apply { id = this@JennysHomePhone.id }

        val dto = phoneToPhoneDtoConverter.convert(entityPersisted)
    }

    object DansCell {
        val id = UUID.fromString("7e84bcfa-1d3e-11ed-861d-0242ac120002")
        val type = PhoneType.Cell
        const val number = "(778) 211-1992"
        val entityDetached: Phone
            get() = Phone(
                number = number,
                type = type
            ).apply { id = this@DansCell.id }

        val entityTransient: Phone
            get() = Phone(
                number = number,
                type = type
            )

        val dto = phoneToPhoneDtoConverter.convert(entityDetached)

        // TODO Dans cell dto create
    }


    object JennysWorkPhone {
        val id = UUID.fromString("1ac8f1d3-ca0d-4160-8c04-c548a1bfcb2a")
        val type = PhoneType.Work
        const val number = "(604) 532-4327"

        val entityPersisted = Phone(
            number = number,
            type = type
        ).apply { id = this@JennysWorkPhone.id }
        val dto = phoneToPhoneDtoConverter.convert(entityPersisted)
    }


}