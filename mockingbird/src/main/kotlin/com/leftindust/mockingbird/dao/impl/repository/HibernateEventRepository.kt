package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.Event
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
interface HibernateEventRepository : JpaRepository<Event, UUID> {
    fun getAllByTitleEquals(title: String): List<Event>

    @Query("select e from Event e where (e.startTime between :rangeStart and :rangeEnd and e.endTime between :rangeStart and :rangeEnd) or e.reoccurrence is not null")
    fun getAllInRangeOrReoccurrenceIsNotNull(
        @Param("rangeStart") rangeStart: Timestamp,
        @Param("rangeEnd") rangeEnd: Timestamp
    ): List<Event>

    @Query("select e from Event e where e.id = ?1")
    @EntityGraph(value = "Event.patients")
    fun getByIdWithPatients(id: UUID): Event
}