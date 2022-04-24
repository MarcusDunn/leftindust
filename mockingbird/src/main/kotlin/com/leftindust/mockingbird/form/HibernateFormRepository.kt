package com.leftindust.mockingbird.form

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateFormRepository : JpaRepository<Form, UUID>