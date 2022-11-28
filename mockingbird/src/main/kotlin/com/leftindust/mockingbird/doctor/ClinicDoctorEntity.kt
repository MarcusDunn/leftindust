package com.leftindust.mockingbird.doctor

import com.google.common.base.Objects
import com.leftindust.mockingbird.clinic.ClinicEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class ClinicDoctorEntity(
    @Id
    @ManyToOne
    val clinic: ClinicEntity,
    @Id
    @ManyToOne
    val doctor: DoctorEntity,
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