package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.patient.PatientEntity
import java.util.Objects
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne


@Entity
class DoctorPatientEntity(
    @Id
    @ManyToOne
    val doctor: DoctorEntity,
    @Id
    @ManyToOne
    val patient: PatientEntity,
) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }
        val that = other as DoctorPatientEntity
        return Objects.equals(doctor, that.doctor) && Objects.equals(patient, that.patient)
    }

    override fun hashCode(): Int {
        return Objects.hash(doctor, patient)
    }
}