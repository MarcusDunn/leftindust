package com.leftindust.mockingbird.group

import java.util.*
import org.springframework.data.repository.CrudRepository

interface HibernateGroupRepository : CrudRepository<MediqGroup, UUID>

