package com.leftindust.mockingbird.clinic

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateClinicRepository : JpaRepository<Clinic, UUID>
