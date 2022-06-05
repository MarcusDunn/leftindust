package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val readPatientService: ReadPatientService,
) : ReadDoctorService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): Flow<Doctor>? {
        val patient = readPatientService.getByPatientId(patientDtoId) ?: run {
            logger.trace { "Returning null from getByPatientId. Could not find a patient with id $patientDtoId" }
            return null
        }
        return patient.doctors.asFlow().map { it.doctor }
    }

    override suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): Flow<Doctor>? {
        TODO("Not yet implemented")
    }

    override suspend fun getByUserUid(userUid: String): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(range: RangeDto): Flow<Doctor> {
        TODO("Not yet implemented")
    }

    override suspend fun searchByExample(example: GraphQLDoctorExample): Flow<Doctor> {
        TODO("Not yet implemented")
    }

}