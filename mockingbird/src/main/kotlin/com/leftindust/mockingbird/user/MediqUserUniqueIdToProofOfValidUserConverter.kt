package com.leftindust.mockingbird.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MediqUserUniqueIdToProofOfValidUserConverter(private val firebaseAuth: FirebaseAuth) : FallibleConverter<MediqUserDto.MediqUserUniqueId, ProofOfValidUser> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: MediqUserDto.MediqUserUniqueId): ProofOfValidUser? {
        try {
            firebaseAuth.getUser(source.value)
        } catch (e: FirebaseAuthException) {
            logger.debug(e) { "[${source.value}] is not a valid firebase uid." }
            logger.debug { FailedConversionMessage(source) }
            return null
        } catch (e: IllegalArgumentException) {
            logger.debug { FailedConversionMessage(source) }
            logger.debug(e) { "[${source.value}] is not a valid firebase uid." }
            return null
        }

        return ProofOfValidUser
    }
}