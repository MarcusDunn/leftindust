package com.leftindust.mockingbird.person

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface NameInfoRepository : CrudRepository<NameInfoEntity, UUID> {

}
