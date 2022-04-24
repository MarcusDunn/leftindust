package com.leftindust.mockingbird.patient

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("FunctionName")
interface HibernatePatientRepository : JpaRepository<Patient, UUID> {
    fun findByUser_UniqueId(user_uniqueId: String): Patient?
}