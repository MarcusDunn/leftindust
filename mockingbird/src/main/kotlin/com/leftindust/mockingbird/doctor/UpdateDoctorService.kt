package com.leftindust.mockingbird.doctor

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('UPDATE_DOCTOR')")
interface UpdateDoctorService {
    suspend fun editDoctor(updateDoctor: UpdateDoctor): Doctor?
}