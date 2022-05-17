package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.group.MediqGroup
import org.springframework.stereotype.Component

@Component
class MediqGroupToUserGroupDtoConverter : InfallibleConverter<MediqGroup, MediqGroupDto> {
    override fun convert(source: MediqGroup): MediqGroupDto {
        return MediqGroupDto(MediqGroupDto.MediqGroupId(source.id!!), source.name)
    }
}