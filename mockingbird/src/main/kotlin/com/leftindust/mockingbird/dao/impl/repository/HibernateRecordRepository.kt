package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.MediqRecord
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateRecordRepository : JpaRepository<MediqRecord, UUID> {
    fun getAllByPatientId(patient_id: UUID): Collection<MediqRecord>
}
