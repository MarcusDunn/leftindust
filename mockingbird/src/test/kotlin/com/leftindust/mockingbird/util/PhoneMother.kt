package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneType
import com.leftindust.mockingbird.phone.toPhoneDto
import dev.forkhandles.result4k.onFailure
import java.util.*

object PhoneMother {

    object JennysHomePhone {
        val id = UUID.fromString("548143f2-a3ba-4c95-ac3a-27e76c37149e")
        val type = PhoneType.Home
        const val number = "+16040734427"
        val entityPersisted = Phone(
            number = number,
            type = type
        ).apply { id = this@JennysHomePhone.id }

        val dto = entityPersisted.toPhoneDto().onFailure { throw it.reason.toMockingbirdException() }
    }

    object DansCell {
        val id = UUID.fromString("7e84bcfa-1d3e-11ed-861d-0242ac120002")
        val type = PhoneType.Cell
        const val number = "+17782111992"
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

        val dto = entityDetached.toPhoneDto().onFailure { throw it.reason.toMockingbirdException() }

        val createDto = CreatePhoneGraphQlDto(
            number = number,
            type = type
        )

        val createUpdatedDto = CreatePhoneGraphQlDto(
            number = "+1778330112",
            type = PhoneType.Home
        )

    }


    object JennysWorkPhone {
        val id = UUID.fromString("1ac8f1d3-ca0d-4160-8c04-c548a1bfcb2a")
        val type = PhoneType.Work
        const val number = "+16045324327"

        val entityPersisted = Phone(
            number = number,
            type = type
        ).apply { id = this@JennysWorkPhone.id }
        val dto = entityPersisted.toPhoneDto().onFailure { throw it.reason.toMockingbirdException() }
    }


}