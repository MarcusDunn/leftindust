package com.leftindust.mockingbird.doctor

import java.util.UUID
import org.springframework.data.repository.PagingAndSortingRepository

interface DoctorRepository : PagingAndSortingRepository<DoctorEntity, UUID> {

}