package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_CLINIC')")
interface CreateClinicService {

    suspend fun addClinic(createClinic: CreateClinic): Clinic
}