package com.leftindust.mockingbird.auth

/**
 * an implementation agnostic wrapper around the auth service currently used
 * @property uid the uid of the user holding the token
 */
interface MediqToken {
    val uid: String?
    fun isVerified(): Boolean
    companion object
}