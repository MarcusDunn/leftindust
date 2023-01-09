package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure


fun MediqUser.toMediqUserDto(): Result4k<MediqUserDto, ConversionError<MediqUser, MediqUserDto>> {
    return Success(
        MediqUserDto(
            id = MediqUserDto.MediqUserUniqueId(uniqueId),
            group = group?.let { it.toMediqGroupDto().onFailure { throw it.reason.toMockingbirdException() } }
        )
    )
}
