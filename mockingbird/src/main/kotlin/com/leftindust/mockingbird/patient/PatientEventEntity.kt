package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.persistance.JpaEntity
import java.util.Objects
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class PatientEventEntity(
    @Id @ManyToOne val patient: PatientEntity,
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