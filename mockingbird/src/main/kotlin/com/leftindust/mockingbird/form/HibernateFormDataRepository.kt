package com.leftindust.mockingbird.form

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateFormDataRepository : JpaRepository<FormData, UUID> {
    fun getByPatient_Id(patient_id: UUID): List<FormData>
}