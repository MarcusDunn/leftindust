package com.leftindust.mockingbird.user

import com.google.firebase.auth.UserRecord
import com.leftindust.mockingbird.ConversionError
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success


fun UserRecord.toUserAccountDetails(): Result4k<UserAccountDetails, ConversionError<UserRecord, UserAccountDetails>> {
    return Success(
        UserAccountDetailsDto(
            isRegistered = true,
            email = email
        )
    )
}
