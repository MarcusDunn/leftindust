package com.leftindust.mockingbird.email

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface EmailRepository : CrudRepository<EmailEntity, UUID> {

}
