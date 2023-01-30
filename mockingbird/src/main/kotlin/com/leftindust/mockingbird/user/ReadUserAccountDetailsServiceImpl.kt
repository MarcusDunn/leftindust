package com.leftindust.mockingbird.user

import com.google.firebase.auth.FirebaseAuth
import dev.forkhandles.result4k.onFailure
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class ReadUserAccountDetailsServiceImpl(
    private val firebaseAuth: FirebaseAuth,
) : ReadUserAccountDetailsService {
    private val logger = KotlinLogging.logger { }

    override fun getUserInfoByMediqUserUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): UserAccountDetails? {
        val user = firebaseAuth.getUser(mediqUserUniqueId.value) ?: return null
        return user.toUserAccountDetails().onFailure { throw it.reason.toMockingbirdException() }
    }
}
