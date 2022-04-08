package com.leftindust.mockingbird.dao.impl.repository

import com.leftindust.mockingbird.dao.entity.Phone
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HibernatePhoneRepository : JpaRepository<Phone, UUID>