package com.leftindust.mockingbird.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.extensions.doThenNull
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class ReadUserAccountDetailsServiceImpl(
    private val userRecordToUserAccountDetailsConverter: InfallibleConverter<UserRecord, UserAccountDetails>,
    private val firebaseAuth: FirebaseAuth,
) : ReadUserAccountDetailsService {
    private val logger = KotlinLogging.logger { }

    override fun getUserInfoByMediqUserUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): UserAccountDetails? {
        val user = firebaseAuth.getUser(mediqUserUniqueId.value)
            ?: return doThenNull { logger.debug { "returning null from getUserInfoByMediqUserUniqueId for $mediqUserUniqueId" } }
        return userRecordToUserAccountDetailsConverter.convert(user)
    }
}