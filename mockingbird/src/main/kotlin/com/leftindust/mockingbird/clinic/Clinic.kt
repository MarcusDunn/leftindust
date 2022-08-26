package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.AddedElementMessage
import com.leftindust.mockingbird.RemovedElementMessage
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.doctor.Doctor
import mu.KotlinLogging
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.OneToMany
import javax.persistence.OneToOne

interface Clinic {
    val id: UUID
    val name: String
    val address: Address
    val doctors: Set<ClinicDoctorEntity>
}