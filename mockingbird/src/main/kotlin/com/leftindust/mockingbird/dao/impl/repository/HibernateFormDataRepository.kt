package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.FormData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateFormDataRepository : JpaRepository<FormData, UUID> {
    fun getByPatient_Id(patient_id: UUID): List<FormData>
}