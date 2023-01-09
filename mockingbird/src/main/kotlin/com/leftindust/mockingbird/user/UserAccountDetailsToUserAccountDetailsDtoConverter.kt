package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success

fun UserAccountDetails.toUserAccountDetailsDto(): Result4k<UserAccountDetailsDto, ConversionError<UserAccountDetails, UserAccountDetailsDto>> {
    return Success(
        UserAccountDetailsDto(
            isRegistered = isRegistered,
            email = email
        )
    )
}
