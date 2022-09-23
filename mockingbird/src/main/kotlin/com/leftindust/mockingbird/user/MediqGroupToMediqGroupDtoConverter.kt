package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.group.MediqGroup
import org.springframework.stereotype.Component

@Component
class MediqGroupToMediqGroupDtoConverter : InfallibleConverter<MediqGroup, MediqGroupDto> {
    override fun convert(source: MediqGroup): MediqGroupDto {
        return MediqGroupDto(
            id = MediqGroupDto.MediqGroupId(source.id ?: throw NullEntityIdInConverterException(source)),
            name = source.name
        )
    }
}