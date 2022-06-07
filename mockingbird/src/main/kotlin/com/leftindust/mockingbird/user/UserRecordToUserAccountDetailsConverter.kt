package com.leftindust.mockingbird.user

import com.google.firebase.auth.UserRecord
import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class UserRecordToUserAccountDetailsConverter : InfallibleConverter<UserRecord, UserAccountDetails> {
    override fun convert(source: UserRecord): UserAccountDetails {
        return UserAccountDetailsDto(
            isRegistered = true,
            email = source.email
        )
    }
}