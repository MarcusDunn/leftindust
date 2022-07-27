package com.leftindust.mockingbird.patient

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface PatientRepository : CrudRepository<Patient, UUID> {
}