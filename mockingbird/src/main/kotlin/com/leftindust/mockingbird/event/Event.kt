package com.leftindust.mockingbird.event

import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.extensions.onUndefined
import com.leftindust.mockingbird.graphql.types.GraphQLUtcTime
import com.leftindust.mockingbird.patient.Patient
import java.sql.Timestamp
import javax.persistence.*

@Entity
@NamedEntityGraph(
    name = "Event.patients",
    attributeNodes = [NamedAttributeNode("patients")],
)
class Event(
    var title: String,
    var description: String?,
    var startTime: Timestamp,
    var endTime: Timestamp?,
    var allDay: Boolean = false,
    @ManyToMany(mappedBy = "events")
    val doctors: MutableSet<Doctor>,
    @ManyToMany(mappedBy = "events")
    val patients: MutableSet<Patient>,
    @OneToOne(optional = true, mappedBy = "event")
    var visit: Visit? = null,
    @Embedded
    var reoccurrence: Reoccurrence? = null,
) : AbstractJpaPersistable() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Event

        if (title != other.title) return false
        if (description != other.description) return false
        if (startTime != other.startTime) return false
        if (endTime != other.endTime) return false
        if (allDay != other.allDay) return false
        if (doctors != other.doctors) return false
        if (patients != other.patients) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + startTime.hashCode()
        result = 31 * result + endTime.hashCode()
        result = 31 * result + allDay.hashCode()
        result = 31 * result + doctors.hashCode()
        result = 31 * result + patients.hashCode()
        return result
    }


    fun update(event: GraphQLEventEditInput, newDoctors: Set<Doctor>?, newPatients: Set<Patient>?) {
        title = event.title ?: title
        description = event.description.onUndefined(description)
        startTime = event.start?.toTimestamp() ?: startTime
        endTime = event.end.onUndefined(endTime?.time?.let { GraphQLUtcTime(it) })?.toTimestamp()
        allDay = event.allDay ?: allDay
        newDoctors?.let { notNullNewDoctors ->
            doctors.forEach { it.events.removeIf { evnt -> evnt == this } }
            doctors.clear()
            doctors.addAll(notNullNewDoctors)
            doctors.forEach { it.addEvent(this) }
        }
        newPatients?.let { notNullNewPatients ->
            patients.forEach { it.events.removeIf { evnt -> evnt == this } }
            patients.clear()
            patients.addAll(notNullNewPatients)
            patients.forEach { it.addEvent(this) }
        }
        reoccurrence = when (val recc = event.recurrence) {
            is OptionalInput.Undefined -> reoccurrence
            is OptionalInput.Defined -> recc.value?.let { Reoccurrence(it) }
        }
    }

    constructor(graphQLEventInput: GraphQLEventInput, doctors: Set<Doctor>, patients: Set<Patient>) : this(
        title = graphQLEventInput.title,
        description = graphQLEventInput.description,
        startTime = graphQLEventInput.start.toTimestamp(),
        endTime = graphQLEventInput.end.toTimestamp(),
        allDay = graphQLEventInput.allDay,
        reoccurrence = graphQLEventInput.recurrence?.let { Reoccurrence(it) },
        doctors = doctors.toMutableSet(),
        patients = patients.toMutableSet()
    )

    fun clone(): Event {
        return Event(
            title = title,
            description = description,
            startTime = startTime,
            endTime = endTime,
            allDay = allDay,
            doctors = doctors.toMutableSet(),
            patients = patients.toMutableSet(),
            reoccurrence = reoccurrence,
        )
    }
}