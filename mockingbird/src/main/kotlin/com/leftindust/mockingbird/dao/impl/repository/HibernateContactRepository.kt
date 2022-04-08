package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.EmergencyContact
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateContactRepository : JpaRepository<EmergencyContact, UUID> {
    fun getAllByPatient_Id(patient_id: UUID): Collection<EmergencyContact>
}
