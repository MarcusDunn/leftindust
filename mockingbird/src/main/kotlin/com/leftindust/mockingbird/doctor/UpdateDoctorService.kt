package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.IntoMockingbirdException
import dev.forkhandles.result4k.Result4k
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('UPDATE_DOCTOR')")
interface UpdateDoctorService {
    suspend fun editDoctor(updateDoctor: UpdateDoctor): Result4k<Doctor, IntoMockingbirdException>
}