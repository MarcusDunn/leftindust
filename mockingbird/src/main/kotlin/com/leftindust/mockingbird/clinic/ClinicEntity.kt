package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.AddedElementMessage
import com.leftindust.mockingbird.RemovedElementMessage
import com.leftindust.mockingbird.address.AddressEntity
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.doctor.DoctorEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import mu.KotlinLogging
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

private val logger = KotlinLogging.logger { }

@Entity
class ClinicEntity(
    @Column(nullable = false)
    var name: String,
    @OneToOne(optional = false, cascade = [CascadeType.PERSIST])
    var address: AddressEntity,
    @OneToMany(mappedBy = "clinic")
    var doctors: MutableSet<ClinicDoctorEntity>,
) : AbstractJpaPersistable() {
    fun clearDoctors() {
        doctors.forEach { removeDoctor(it.doctor) }
    }

    fun addDoctor(doctor: DoctorEntity) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.add(clinicDoctor)
        doctor.clinics.add(clinicDoctor)
        logger.trace { AddedElementMessage(doctor, doctor::clinics, clinicDoctor) }
    }

    fun removeDoctor(doctor: DoctorEntity) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.remove(clinicDoctor)
        doctor.clinics.remove(clinicDoctor)
        logger.trace { RemovedElementMessage(doctor, doctor::clinics, clinicDoctor) }
    }
}
