package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.AccessControlList
import com.leftindust.mockingbird.dao.entity.MediqGroup
import com.leftindust.mockingbird.dao.entity.MediqUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateAclRepository : JpaRepository<AccessControlList, UUID> {
    fun findAllByMediqUser(user: MediqUser): Collection<AccessControlList>
    fun findAllByGroup(group: MediqGroup): Collection<AccessControlList>
}
