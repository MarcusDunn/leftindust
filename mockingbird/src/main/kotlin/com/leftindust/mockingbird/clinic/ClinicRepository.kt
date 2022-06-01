package com.leftindust.mockingbird.clinic

import java.util.UUID
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ClinicRepository : CrudRepository<Clinic, UUID> {
}
