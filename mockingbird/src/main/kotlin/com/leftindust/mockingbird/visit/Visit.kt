package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.event.Event
import javax.persistence.CascadeType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

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