package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.AddedElementMessage
import com.leftindust.mockingbird.ClearedEntityCollectionMessage
import com.leftindust.mockingbird.RemovedElementMessage
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.doctor.Doctor
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

@Entity
class Clinic(
    @Column(nullable = false)
    var name: String,
    @OneToOne(optional = false)
    var address: Address,
    @OneToMany(mappedBy = "clinic", cascade = [CascadeType.ALL], orphanRemoval = true)
    var doctors: MutableSet<ClinicDoctorEntity> = mutableSetOf(),
) : AbstractJpaPersistable() {
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