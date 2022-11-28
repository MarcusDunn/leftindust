package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.event.Event
import jakarta.persistence.CascadeType
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(columnNames = ["event_id"])]
)
class Visit(
    @OneToOne(cascade = [CascadeType.MERGE], optional = false)
    var event: Event,
    var title: String? = null,
    var description: String? = null,
    @ElementCollection
    // stored as URLS to the code
    var icds: Set<String> = emptySet(),
) : AbstractJpaPersistable()