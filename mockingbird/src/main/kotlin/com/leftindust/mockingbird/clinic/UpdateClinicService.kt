package com.leftindust.mockingbird.clinic

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('UPDATE_CLINIC')")
interface UpdateClinicService {
    suspend fun editClinic(clinicEdit: ClinicEdit): Clinic?
}