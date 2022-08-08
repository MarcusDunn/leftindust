package com.leftindust.mockingbird.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface MediqUserRepository : JpaRepository<MediqUser, String> {

}