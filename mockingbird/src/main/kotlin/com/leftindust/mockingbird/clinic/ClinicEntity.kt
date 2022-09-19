package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.AddedElementMessage
import com.leftindust.mockingbird.RemovedElementMessage
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import mu.KotlinLogging
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

private val logger = KotlinLogging.logger { }

@Entity
class ClinicEntity(
    @Column(nullable = false)
    var name: String,
    @OneToOne(optional = false, cascade = [CascadeType.PERSIST])
    var address: Address,
    @OneToMany(mappedBy = "clinic")
    var doctors: MutableSet<ClinicDoctorEntity>,
) : AbstractJpaPersistable() {
    fun clearDoctors() {
        doctors.forEach { removeDoctor(it.doctor) }
    }

    fun addDoctor(doctor: Doctor) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.add(clinicDoctor)
        doctor.clinics.add(clinicDoctor)
        logger.trace { AddedElementMessage(doctor, doctor::clinics, clinicDoctor) }
    }

    fun removeDoctor(doctor: Doctor) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.remove(clinicDoctor)
        doctor.clinics.remove(clinicDoctor)
        logger.trace { RemovedElementMessage(doctor, doctor::clinics, clinicDoctor) }
    }
}
