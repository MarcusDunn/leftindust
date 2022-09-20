package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.ReadPatientService
import org.springframework.data.repository.findByIdOrNull
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateEventServiceImpl(
    private val eventRepository: HibernateEventRepository,
    private val readPatientService: ReadPatientService,
) : CreateEventService {
    override suspend fun addEvent(createEvent: CreateEvent): Event {
        val patients = createEvent.patients.map {
            readPatientService.getByPatientId(it)
                ?: throw IllegalArgumentException("No Patient with id $it")
        }
        val doctors = createEvent.doctors.map {
            doctorRepository.findByIdOrNull(it.value)
                ?: throw IllegalArgumentException("No Doctor with id $it")
        }

        val event = Event(createEvent.iCal, mutableSetOf(), mutableSetOf(), null)

        patients.forEach { it.addEvent(event) }
        doctors.forEach { it.addEvent(event) }

        return eventRepository.save(event)
    }
}