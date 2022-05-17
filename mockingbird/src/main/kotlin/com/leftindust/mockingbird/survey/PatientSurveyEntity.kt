package com.leftindust.mockingbird.survey

import com.google.common.base.Objects
import com.leftindust.mockingbird.doctor.DoctorPatientEntity
import com.leftindust.mockingbird.patient.Patient
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class PatientSurveyEntity(
    @Id
    @ManyToOne
    val patient: Patient,
    @Id
    @OneToOne
    val survey: Survey,
) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }
        val that = other as DoctorPatientEntity
        return Objects.equal(patient, that.patient) && Objects.equal(survey, that.patient)
    }

    override fun hashCode(): Int {
        return Objects.hashCode(patient, survey)
    }
}