package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.user.CreateMediqUserDto
import com.leftindust.mockingbird.user.CreateMediqUserDtoToCreateMediqUserConverter
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.MediqUserUniqueIdToProofOfValidUserConverter
import com.leftindust.mockingbird.user.toMediqUserDto
import dev.forkhandles.result4k.onFailure
import io.mockk.every
import io.mockk.mockk

object MediqUserMother {

    object Marcus {
        const val uniqueId = "pfAfnZU8eEVHmeA9l2J68cmZrl89"
        val graphqlUniqueId = MediqUserDto.MediqUserUniqueId(uniqueId)

        val mediqUserUniqueIdToProofOfValidUserConverter = MediqUserUniqueIdToProofOfValidUserConverter(mockk() {
            every { getUser(uniqueId) } returns mockk()
        })
        val createMediqUserDtoToCreateMediqUserConverter = CreateMediqUserDtoToCreateMediqUserConverter(mediqUserUniqueIdToProofOfValidUserConverter)

        const val firstName = "Marcus"
        const val lastName = "Dunn"
        val middleName: String? = null
        val nameInfoEntity = NameInfoEntity(firstName, lastName, middleName)
        val group: MediqGroup? = null
        val entity = MediqUser(
            uniqueId = uniqueId,
            group = group,
            nameInfoEntity = nameInfoEntity
        )

        val domain = entity

        val dto = domain.toMediqUserDto().onFailure { throw it.reason.toMockingbirdException() }

        val createNameInfoDto = CreateNameInfoDto(
            firstName = firstName,
            middleName = middleName,
            lastName = lastName
        )

        val createDto = CreateMediqUserDto(
            uid = graphqlUniqueId,
            nameInfo = createNameInfoDto,
            group = null,
            doctor = null,
        )

        val create = createMediqUserDtoToCreateMediqUserConverter.convert(createDto)!!
    }
}
