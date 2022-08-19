package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize
@PreAuthorize("hasAuthority('READ_ADDRESS')")
interface ReadAddressService {
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Address>?

    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<Address>?
}
