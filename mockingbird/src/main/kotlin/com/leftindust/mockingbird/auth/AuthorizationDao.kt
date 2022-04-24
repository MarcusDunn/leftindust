package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.Blocking

interface AuthorizationDao {
    @Blocking
    fun getRolesForUserByUid(uid: String): List<AccessControlList>

    @Blocking
    fun isAdmin(uid: String): Boolean

    @Blocking
    fun isPatient(uid: String): Boolean
}