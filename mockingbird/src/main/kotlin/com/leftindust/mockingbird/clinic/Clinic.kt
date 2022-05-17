package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.doctor.Doctor
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

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
        doctors.forEach { it.doctor.clinics.remove(it) }
        doctors.clear()
    }

    fun addDoctor(doctor: Doctor) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.add(clinicDoctor)
        doctor.clinics.add(clinicDoctor)
    }

    fun removeDoctor(doctor: Doctor) {
        val clinicDoctor = ClinicDoctorEntity(this, doctor)
        doctors.remove(clinicDoctor)
        doctor.clinics.remove(clinicDoctor)
    }
}