package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.extensions.doThenNull
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.visit.ReadVisitService
import com.leftindust.mockingbird.visit.VisitDto
import java.time.Period
import java.time.ZonedDateTime
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadEventServiceImpl(
    private val eventRepository: HibernateEventRepository,
    private val readPatientService: ReadPatientService,
    private val readDoctorService: ReadDoctorService,
    private val readVisitService: ReadVisitService,
) : ReadEventService {
    private val logger = KotlinLogging.logger { }

    override suspend fun getByEventId(eventId: EventDto.EventDtoId): Event? {
        return eventRepository
            .findById(eventId.value)
            .orElse(null)
            ?: doThenNull { logger.debug { "returning null from getByEventId for $eventId" } }
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Event>? {
        return readPatientService
            .getByPatientId(patientId)
            ?.events
            ?.map { it.event }
            ?.asFlow()
            ?: doThenNull { logger.debug { "returning null from getByPatientId for $patientId" } }
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Flow<Event>? {
        return readDoctorService
            .getByDoctorId(doctorId)
            ?.events
            ?.map { it.event }
            ?.asFlow()
            ?: doThenNull { logger.debug { "returning null from getByDoctorId for $doctorId" } }
    }

    override suspend fun getByVisitId(visitId: VisitDto.VisitDtoId): Event? {
        return readVisitService
            .getByVisitId(visitId)
            ?.event
            ?: doThenNull { logger.debug { "returning null from getByVisitId for $visitId" } }
    }
}