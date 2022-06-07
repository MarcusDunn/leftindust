package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateEventServiceImpl(
    private val eventRepository: HibernateEventRepository,
    private val readDoctorService: ReadDoctorService,
    private val readPatientService: ReadPatientService,
) : CreateEventService {
    private val logger = KotlinLogging.logger { }

    override suspend fun addEvent(createEvent: CreateEvent): Event {
        val patients = getPatients(createEvent)
        val doctors = getDoctors(createEvent)

        val event = Event(createEvent.iCal, mutableSetOf(), mutableSetOf(), null)

        patients.forEach { it.addEvent(event) }
        doctors.forEach { it.addEvent(event) }

        return eventRepository.save(event)
    }

    private suspend fun getPatients(createEvent: CreateEvent): List<Patient> {
        val patients = createEvent.patients.map { it to readPatientService.getByPatientId(it) }
        patients.filter { it.second == null }.forEach { logger.debug { "did not find a patient for ${it.first}" } }
        return patients.mapNotNull { it.second }
    }

    private suspend fun getDoctors(createEvent: CreateEvent): List<Doctor> {
        val doctors = createEvent.doctors.map { it to readDoctorService.getByDoctorId(it) }
        doctors.filter { it.second == null }.forEach { logger.debug { "did not find a doctor for ${it.first}" } }
        return doctors.mapNotNull { it.second }
    }
}