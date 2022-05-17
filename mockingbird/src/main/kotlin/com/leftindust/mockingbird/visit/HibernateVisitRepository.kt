package com.leftindust.mockingbird.visit

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface HibernateVisitRepository : JpaRepository<Visit, UUID> {
}