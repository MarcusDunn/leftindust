package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.group.MediqGroup
import org.springframework.stereotype.Component

@Component
class MediqUserToUserDtoConverter(private val mediqGroupToMediqGroupDtoConverter: InfallibleConverter<MediqGroup, MediqGroupDto>) : InfallibleConverter<MediqUser, MediqUserDto> {
    override fun convert(source: MediqUser): MediqUserDto {
        return MediqUserDto(
            MediqUserDto.MediqUserUniqueId(source.uniqueId),
            source.group?.let { mediqGroupToMediqGroupDtoConverter.convert(it) }
        )
    }
}

