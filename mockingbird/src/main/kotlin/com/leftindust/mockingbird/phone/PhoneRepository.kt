package com.leftindust.mockingbird.phone

import java.util.*
import org.springframework.data.repository.CrudRepository

interface PhoneRepository : CrudRepository<Phone, UUID>