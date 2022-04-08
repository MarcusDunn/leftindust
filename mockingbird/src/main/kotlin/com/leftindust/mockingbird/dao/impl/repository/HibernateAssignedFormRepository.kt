package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.AssignedForm
import org.springframework.data.repository.CrudRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateAssignedFormRepository : CrudRepository<AssignedForm, UUID> {
    fun findAllByPatient_Id(patient_id: UUID): Collection<AssignedForm>
}