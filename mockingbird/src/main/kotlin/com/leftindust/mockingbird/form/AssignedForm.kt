package com.leftindust.mockingbird.form

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.patient.Patient
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["id", "patient_id", "form_template_id"])])
class AssignedForm(
    @OneToOne
    val formTemplate: Form,
    @ManyToOne
    val patient: Patient,
) : AbstractJpaPersistable() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AssignedForm

        if (formTemplate != other.formTemplate) return false
        if (patient != other.patient) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + formTemplate.hashCode()
        result = 31 * result + patient.hashCode()
        return result
    }
}