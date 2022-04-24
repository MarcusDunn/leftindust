package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.user.MediqUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateAclRepository : JpaRepository<AccessControlList, UUID> {
    fun findAllByMediqUser(user: MediqUser): Collection<AccessControlList>
    fun findAllByGroup(group: MediqGroup): Collection<AccessControlList>
}
