package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
) : ReadDoctorService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): List<Doctor>? {
        val patient = patientRepository.findByIdOrNull(patientDtoId.value) ?: return null
        return patient.doctors.map { it.doctor.toDoctor().onFailure { throw it.reason.toMockingbirdException() } }
    }

    override suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): Doctor? {
        val doctorEntity = doctorRepository.findByIdOrNull(doctorDtoId.value) ?: return null
        return doctorEntity.toDoctor().onFailure { throw it.reason.toMockingbirdException() }
    }

    override suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): List<Doctor>? {
        TODO("Not yet implemented")
    }

    override suspend fun getByUserUid(userUid: String): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(range: RangeDto): List<Doctor> {
        return doctorRepository
            .findAll(range.toPageable(Sort.sort(Doctor::class.java).by(Doctor::id)))
            .map { it.toDoctor().onFailure { throw it.reason.toMockingbirdException() } }
            .toList()
    }

    override suspend fun searchByExample(example: GraphQLDoctorExample): List<Doctor> {
        TODO("Not yet implemented")
    }

}