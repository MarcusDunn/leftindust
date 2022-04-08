package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.NameInfo
import javax.persistence.EntityNotFoundException

interface NameInfoDao {
    @Blocking
    fun findByUniqueId(uid: String, requester: MediqToken): NameInfo?

    @Blocking
    fun getByUniqueId(uid: String, requester: MediqToken): NameInfo =
        findByUniqueId(uid, requester) ?: throw EntityNotFoundException("Could not find name with uid $uid")
}