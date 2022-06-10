package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.persistance.JpaEntity
import java.util.Objects
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class PatientEventEntity(
    @Id @ManyToOne val patient: Patient,
    @Id @ManyToOne val event: Event,
) : java.io.Serializable, JpaEntity {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }
        val that = other as PatientEventEntity
        return Objects.equals(patient, that.patient) && Objects.equals(event, that.event)
    }

    override fun hashCode(): Int {
        return Objects.hash(patient, event)
    }
}