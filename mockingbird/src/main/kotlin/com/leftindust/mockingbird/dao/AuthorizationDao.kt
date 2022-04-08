package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.dao.entity.AccessControlList

interface AuthorizationDao {
    @Blocking
    fun getRolesForUserByUid(uid: String): List<AccessControlList>
    @Blocking
    fun isAdmin(uid: String): Boolean
    @Blocking
    fun isPatient(uid: String): Boolean
}