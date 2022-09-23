package com.leftindust.mockingbird.patient

import java.util.UUID
import org.springframework.data.repository.PagingAndSortingRepository

interface PatientRepository : PagingAndSortingRepository<Patient, UUID> {
}