package com.leftindust.mockingbird.visit

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

@Suppress("FunctionName")
interface HibernateVisitRepository : JpaRepository<Visit, UUID> {
    fun findByEvent_Id(event_id: UUID): Visit?

    @Query("select v from Visit v where v.id = ?1")
    @EntityGraph("Visit.event.patients")
    fun getByIdWithEventPatients(id: UUID): Visit
}