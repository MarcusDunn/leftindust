package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.group.MediqGroup
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun MediqGroup.toMediqGroupDto(): Result4k<MediqGroupDto, ConversionError<MediqGroup, MediqGroupDto>> {

    return Success(
            MediqGroupDto(
                    id = MediqGroupDto.MediqGroupId(id ?: throw NullEntityIdInConverterException(this)),
                    name = name
            )
    )

}