package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.Patient
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("FunctionName")
interface HibernatePatientRepository : JpaRepository<Patient, UUID> {
    fun findByUser_UniqueId(user_uniqueId: String): Patient?
}