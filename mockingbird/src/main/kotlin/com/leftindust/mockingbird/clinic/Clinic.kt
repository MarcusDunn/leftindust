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

private val logger = KotlinLogging.logger { }

class Clinic(
    val id: UUID,
    @Column(nullable = false)
    var name: String,
    @OneToOne(optional = false, cascade = [CascadeType.PERSIST])
    var address: Address,
    @OneToMany(mappedBy = "clinic")
    var doctors: MutableSet<ClinicDoctorEntity> = mutableSetOf()
) {
    fun clearDoctors() {
        doctors.forEach { removeDoctor(it.doctor) }
        logger.trace { ClearedEntityCollectionMessage(this, this::doctors) }
    }

    fun addDoctor(doctor: Doctor) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.add(clinicDoctor)
        logger.trace { AddedElementMessage(this, this::doctors, clinicDoctor) }
        doctor.clinics.add(clinicDoctor)
        logger.trace { AddedElementMessage(doctor, doctor::clinics, clinicDoctor) }
    }

    fun removeDoctor(doctor: Doctor) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.remove(clinicDoctor)
        logger.trace { RemovedElementMessage(this, this::doctors, clinicDoctor) }
        doctor.clinics.remove(clinicDoctor)
        logger.trace { RemovedElementMessage(doctor, doctor::clinics, clinicDoctor) }
    }
}