package com.leftindust.mockingbird.doctor

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateDoctorPatientRepository : JpaRepository<DoctorPatient, UUID> {
    fun getAllByPatientId(patient_id: UUID): Set<DoctorPatient>
    fun getAllByDoctorId(doctor_id: UUID): Set<DoctorPatient>
}