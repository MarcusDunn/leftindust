package com.leftindust.mockingbird.doctor

import com.google.common.base.Objects
import com.leftindust.mockingbird.clinic.Clinic
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class ClinicDoctorEntity(
    @Id
    @ManyToOne
    val clinic: Clinic,
    @Id
    @ManyToOne
    val doctor: Doctor,
): java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }
        val that = other as DoctorPatientEntity
        return Objects.equal(doctor, that.doctor) && Objects.equal(clinic, that.patient)
    }

    override fun hashCode(): Int {
        return Objects.hashCode(doctor, clinic)
    }
}