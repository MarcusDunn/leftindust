package com.leftindust.mockingbird.patient

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface HibernatePatientRepository : CrudRepository<Patient, UUID> {
}