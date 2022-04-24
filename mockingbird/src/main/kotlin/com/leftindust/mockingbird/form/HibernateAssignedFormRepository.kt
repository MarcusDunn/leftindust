package com.leftindust.mockingbird.form

import org.springframework.data.repository.CrudRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateAssignedFormRepository : CrudRepository<AssignedForm, UUID> {
    fun findAllByPatient_Id(patient_id: UUID): Collection<AssignedForm>
}