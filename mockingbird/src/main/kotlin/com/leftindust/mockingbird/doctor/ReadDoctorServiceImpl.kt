package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val readPatientService: ReadPatientService,
) : ReadDoctorService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): List<Doctor>? {
        val patient = readPatientService.getByPatientId(patientDtoId) ?: return null
        return patient.doctors.map { doctorEntityToDoctorConverter.convert(it.doctor) }
    }

    override suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): Doctor? {
        return doctorRepository.findByIdOrNull(doctorDtoId.value)
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
            .map { doctorEntityToDoctorConverter.convert(it) }
            .toList()
    }

    override suspend fun searchByExample(example: GraphQLDoctorExample): List<Doctor> {
        TODO("Not yet implemented")
    }

}