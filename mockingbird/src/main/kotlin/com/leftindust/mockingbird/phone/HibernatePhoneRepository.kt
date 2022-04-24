package com.leftindust.mockingbird.phone

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernatePhoneRepository : JpaRepository<Phone, UUID>