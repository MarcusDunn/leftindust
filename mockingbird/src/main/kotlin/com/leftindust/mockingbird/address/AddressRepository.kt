package com.leftindust.mockingbird.address

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface AddressRepository : CrudRepository<Address, UUID> {
}