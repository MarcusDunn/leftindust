package com.leftindust.mockingbird.user

data class UserAccountDetailsDto(
    override val isRegistered: Boolean,
    override val email: String?,
) : UserAccountDetails
