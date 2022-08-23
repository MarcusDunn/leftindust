package com.leftindust.mockingbird.clinic

import java.util.UUID
import org.springframework.data.repository.CrudRepository


interface ClinicRepository : CrudRepository<ClinicEntity, UUID> {
}
