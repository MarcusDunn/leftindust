package com.leftindust.mockingbird.auth.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.leftindust.mockingbird.auth.MediqToken
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class VerifiedFirebaseToken constructor(
    private val token: String?,
) : MediqToken {
    private val logger: Logger = LogManager.getLogger()

    private fun verify(token: String?) = FirebaseAuth.getInstance().verifyIdToken(token)

    private val firebaseToken: FirebaseToken? = try {
        verify(token)
    } catch (e: Exception) {
        null
    }

    override val uid = firebaseToken?.uid

    override fun isVerified(): Boolean {
        return this.firebaseToken != null && this.uid != null
    }

    override fun toString(): String {
        return "MediqFireBaseToken(uid=$uid)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VerifiedFirebaseToken

        if (token != other.token) return false

        return true
    }

    override fun hashCode(): Int {
        return token?.hashCode() ?: 0
    }
}