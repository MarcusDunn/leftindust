package com.leftindust.mockingbird.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
interface HibernateUserRepository : JpaRepository<MediqUser, UUID> {
}