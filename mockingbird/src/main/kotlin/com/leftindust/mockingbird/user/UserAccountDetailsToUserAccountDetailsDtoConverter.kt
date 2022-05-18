package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class UserAccountDetailsToUserAccountDetailsDtoConverter : InfallibleConverter<UserAccountDetails, UserAccountDetailsDto> {
    override fun convert(source: UserAccountDetails): UserAccountDetailsDto {
        return UserAccountDetailsDto(
            isRegistered = source.isRegistered,
            email = source.email
        )
    }
}