package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.ReadEventService
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val readPatientService: ReadPatientService,
) : ReadDoctorService {
    private val logger = LoggerFactory.getLogger(ReadDoctorServiceImpl::class.java)

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Doctor>? {
        val patient = readPatientService.getByPatientId(patientId) ?: run {
            logger.trace(LogMessage("Returning null from ${ReadDoctorService::getByPatientId.name}", "ReadPatientService.getByPatientId returned null for $patientId").toString())
            return null
        }
        return patient.doctors.asFlow().map { it.doctor }
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getByClinicId(clinicId: ClinicDto.ClinicDtoId): Flow<Doctor>? {
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