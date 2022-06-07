package com.leftindust.mockingbird.contact

import java.util.*
import org.springframework.data.repository.CrudRepository

interface ContactRepository : CrudRepository<Contact, UUID> {}
