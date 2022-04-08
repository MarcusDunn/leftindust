package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.DoctorPatient
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateDoctorPatientRepository : JpaRepository<DoctorPatient, UUID> {
    fun getAllByPatientId(patient_id: UUID): Set<DoctorPatient>
    fun getAllByDoctorId(doctor_id: UUID): Set<DoctorPatient>
}