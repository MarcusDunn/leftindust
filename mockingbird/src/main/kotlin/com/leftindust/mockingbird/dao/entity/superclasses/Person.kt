package com.leftindust.mockingbird.dao.entity.superclasses

import com.leftindust.mockingbird.dao.entity.*
import java.sql.Timestamp
import javax.persistence.*

@MappedSuperclass
abstract class Person(
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "nameInfo_id", nullable = false)
    var nameInfo: NameInfo,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var addresses: MutableSet<Address> = mutableSetOf(),
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var email: MutableSet<Email> = mutableSetOf(),
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var phones: MutableSet<Phone> = mutableSetOf(),
    @OneToOne
    var user: MediqUser? = null,
    @ManyToMany
    var events: MutableSet<Event> = emptySet<Event>().toMutableSet(),
    @Column(length = 10_000)
    var thumbnail: String? = null
) : AbstractJpaPersistable() {
    init {
        // if user exists, set the user nameInfo to the info stored on the person instead to prevent inconsistencies
        user?.let { it.nameInfo = nameInfo }
    }

    fun getEventsBetween(from: Timestamp, to: Timestamp): List<Event> {
        return this.events.filter { isBetween(from, it, to) }
    }

    private fun isBetween(
        from: Timestamp,
        event: Event,
        to: Timestamp
    ): Boolean {
        return from.before(event.startTime) && to.after(event.startTime)
    }

    abstract fun addEvent(eventEntity: Event)
}