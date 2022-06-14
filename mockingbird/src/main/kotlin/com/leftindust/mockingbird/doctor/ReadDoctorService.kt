package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_DOCTOR')")
interface ReadDoctorService {
    suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): List<Doctor>?
    suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): Doctor?
    suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): List<Doctor>?
    suspend fun getByUserUid(userUid: String): Doctor?
    suspend fun getMany(range: RangeDto): List<Doctor>
    suspend fun searchByExample(example: GraphQLDoctorExample): List<Doctor>
}