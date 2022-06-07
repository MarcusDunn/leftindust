package com.leftindust.mockingbird.record

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateRecordRepository : JpaRepository<MediqRecord, UUID> {
}
