package com.leftindust.mockingbird.event

import org.springframework.stereotype.Repository
import java.util.*
import org.springframework.data.repository.CrudRepository

@Repository
interface HibernateEventRepository : CrudRepository<Event, UUID> {
}