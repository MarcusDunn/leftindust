package com.leftindust.mockingbird.user

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_USER_ACCOUNT_DETAILS')")
interface ReadUserAccountDetailsService {
    fun getUserInfoByMediqUserUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): UserAccountDetails?
}