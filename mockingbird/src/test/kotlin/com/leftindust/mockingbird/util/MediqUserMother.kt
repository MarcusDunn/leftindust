package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.user.CreateMediqUserDto
import com.leftindust.mockingbird.user.CreateMediqUserDtoToCreateMediqUserConverter
import com.leftindust.mockingbird.user.MediqGroupToMediqGroupDtoConverter
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.MediqUserToMediqUserDtoConverter
import com.leftindust.mockingbird.user.MediqUserUniqueIdToProofOfValidUserConverter
import io.mockk.every
import io.mockk.mockk

object MediqUserMother {
    val mediqUserToMediqUserDtoConverter = MediqUserToMediqUserDtoConverter(MediqGroupToMediqGroupDtoConverter())

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
        val nameInfo = NameInfo(firstName, lastName, middleName)
        val group: MediqGroup? = null
        val entity = MediqUser(
            uniqueId = uniqueId,
            group = group,
            nameInfo = nameInfo
        )

        val domain = entity

        val dto = mediqUserToMediqUserDtoConverter.convert(domain)

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