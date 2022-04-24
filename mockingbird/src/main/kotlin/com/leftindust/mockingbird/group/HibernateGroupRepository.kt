package com.leftindust.mockingbird.group

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateGroupRepository : JpaRepository<MediqGroup, UUID>

