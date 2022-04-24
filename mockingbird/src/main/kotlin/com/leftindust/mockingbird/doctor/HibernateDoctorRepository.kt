package com.leftindust.mockingbird.doctor

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateDoctorRepository : JpaRepository<Doctor, UUID> {
    fun findByUser_UniqueId(user_uniqueId: String): Doctor?
    fun getByUser_UniqueId(user_uniqueId: String): Doctor
}