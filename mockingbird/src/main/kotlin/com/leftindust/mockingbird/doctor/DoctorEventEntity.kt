package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.persistance.JpaEntity
import java.util.Objects
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class DoctorEventEntity(
    @Id @ManyToOne val doctor: Doctor,
    @Id @ManyToOne val event: Event,
) : java.io.Serializable, JpaEntity {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }
        val that = other as DoctorEventEntity
        return Objects.equals(doctor, that.doctor) && Objects.equals(event, that.event)
    }

    override fun hashCode(): Int {
        return Objects.hash(doctor, event)
    }
}