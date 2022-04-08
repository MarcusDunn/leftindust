package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.Doctor
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("FunctionName")
interface HibernateDoctorRepository : JpaRepository<Doctor, UUID> {
    fun findByUser_UniqueId(user_uniqueId: String): Doctor?
    fun getByUser_UniqueId(user_uniqueId: String): Doctor
}