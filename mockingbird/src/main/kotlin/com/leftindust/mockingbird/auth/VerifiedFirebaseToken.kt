package com.leftindust.mockingbird.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken

class VerifiedFirebaseToken constructor(
    private val token: String?,
) : MediqToken {

    private fun verify(token: String?) = FirebaseAuth.getInstance().verifyIdToken(token)

    private val firebaseToken: FirebaseToken? = kotlin.runCatching { verify(token) }.getOrNull()

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