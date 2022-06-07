package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_CLINIC')")
interface ReadClinicService {
    suspend fun getByDoctor(did: DoctorDto.DoctorDtoId): Flow<Clinic>?
    suspend fun getByClinicId(cid: ClinicDto.ClinicDtoId): Clinic?
}