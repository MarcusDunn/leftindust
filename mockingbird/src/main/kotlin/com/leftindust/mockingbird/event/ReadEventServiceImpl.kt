package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.visit.ReadVisitService
import com.leftindust.mockingbird.visit.VisitDto
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
        return eventRepository.findById(eventId.value).orElse(null)
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<Event>? {
        val patient = readPatientService.getByPatientId(patientId) ?: return null
        return patient.events.map { it.event }
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Event>? {
        val doctor = readDoctorService.getByDoctorId(doctorId) ?: return null
        return doctor.events.map { it.event }
    }

    override suspend fun getByVisitId(visitId: VisitDto.VisitDtoId): Event? {
        val visit = readVisitService.getByVisitId(visitId) ?: return null
        return visit.event
    }
}