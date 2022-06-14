package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_CLINIC')")
interface ReadClinicService {
    suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): List<Clinic>?
    suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): Clinic?
}