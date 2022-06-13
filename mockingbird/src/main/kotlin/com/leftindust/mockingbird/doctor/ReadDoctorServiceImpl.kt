package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.ReadEventService
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val readPatientService: ReadPatientService,
    private val readEventService: ReadEventService,
) : ReadDoctorService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): List<Doctor>? {
        val patient = readPatientService.getByPatientId(patientDtoId) ?: return null
        return patient.doctors.map { it.doctor }
    }

    override suspend fun getByEventId(eventDtoId: EventDto.EventDtoId): List<Doctor>? {
        val event = readEventService.getByEventId(eventDtoId) ?: return null
        return event.doctors.map { it.doctor }.toList()
    }

    override suspend fun getByDoctorId(doctorDtoId: DoctorDto.DoctorDtoId): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getByClinicId(clinicDtoId: ClinicDto.ClinicDtoId): List<Doctor>? {
        TODO("Not yet implemented")
    }

    override suspend fun getByUserUid(userUid: String): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(range: RangeDto): List<Doctor> {
        TODO("Not yet implemented")
    }

    override suspend fun searchByExample(example: GraphQLDoctorExample): List<Doctor> {
        TODO("Not yet implemented")
    }

}