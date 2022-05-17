package com.leftindust.mockingbird.doctor

import java.util.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DoctorRepository : CrudRepository<Doctor, UUID> {

}