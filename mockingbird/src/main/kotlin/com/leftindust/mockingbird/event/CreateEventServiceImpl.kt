package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientRepository
import org.springframework.data.repository.findByIdOrNull
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateEventServiceImpl(
    private val eventRepository: HibernateEventRepository,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository
) : CreateEventService {
    override suspend fun addEvent(createEvent: CreateEvent): Event {
        val patients = createEvent.patients.map {
            patientRepository.findByIdOrNull(it.value)
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