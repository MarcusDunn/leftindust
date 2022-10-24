package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.email.*
import com.leftindust.mockingbird.person.*
import dev.forkhandles.result4k.valueOrNull
import java.util.*

object NameInfoMother {
    object DansNameInfo {
        val id = UUID.fromString("10845c82-1d17-11ed-861d-0242ac120003")
        val firstName = "Dan"
        val lastName = "Shirvani"
        val middleName = ""
        val entityDetached: NameInfoEntity
            get() = NameInfoEntity(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ).apply { id = this@DansNameInfo.id }

        val entityTransient: NameInfoEntity
            get() = NameInfoEntity(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            )

        val domain = entityDetached.toNameInfo()
        val dto = domain.toNameInfoDto()

        val createDto = CreateNameInfoDto(
            firstName = firstName,
            lastName = lastName,
            middleName = middleName
        )

        val createUpdatedDto = CreateNameInfoDto(
            firstName = "Danny",
            lastName = "Shirvanski",
            middleName = middleName
        )
        val createDomain = createDto.toCreateNameInfo()
    }
}