package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.group.MediqGroup
import org.springframework.stereotype.Component

@Component
class MediqUserToMediqUserDtoConverter(
    private val mediqGroupToMediqGroupDtoConverter: InfallibleConverter<MediqGroup, MediqGroupDto>,
) : InfallibleConverter<MediqUser, MediqUserDto> {
    override fun convert(source: MediqUser): MediqUserDto {
        return MediqUserDto(
            id = MediqUserDto.MediqUserUniqueId(source.uniqueId),
            group = source.group?.let { mediqGroupToMediqGroupDtoConverter.convert(it) }
        )
    }
}