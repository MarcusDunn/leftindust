package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.Form
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernateFormRepository: JpaRepository<Form, UUID>