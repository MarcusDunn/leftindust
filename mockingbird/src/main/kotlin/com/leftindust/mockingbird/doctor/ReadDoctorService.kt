package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_DOCTOR')")
interface ReadDoctorService {
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Doctor>?
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Doctor?
    suspend fun getByClinicId(clinicId: ClinicDto.ClinicDtoId): Flow<Doctor>?
    suspend fun getByUserUid(userUid: String): Doctor?
    suspend fun getMany(range: RangeDto): Flow<Doctor>
    suspend fun searchByExample(example: GraphQLDoctorExample): Flow<Doctor>
}