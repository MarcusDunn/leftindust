package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.extensions.doThenNull
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateEventServiceImpl(
    private val eventRepository: HibernateEventRepository,
    private val readDoctorService: ReadDoctorService,
    private val readPatientService: ReadPatientService,
) : UpdateEventService {
    private val logger = KotlinLogging.logger { }

    override suspend fun updateEvent(updateEvent: UpdateEvent): Event? {
        val event = (eventRepository.findById(updateEvent.eid.value).orElse(null)
            ?: return doThenNull { logger.debug { "returning null from editEvent no Event to update for $updateEvent" } })
        updateIcal(event, updateEvent.ical)
        updatePatients(event, updateEvent.patients)
        updateDoctors(event, updateEvent.doctors)
        return eventRepository.save(event)
    }

    private suspend fun updateDoctors(event: Event, doctors: Updatable<List<DoctorDto.DoctorDtoId>>) {
        when (doctors) {
            is Updatable.Ignore -> {}
            is Updatable.Update -> {
                logger.debug { "removing all doctors from $event" }
                event.removeAllDoctors()
                doctors.value
                    .mapNotNull {
                        readDoctorService.getByDoctorId(it)
                            ?: doThenNull { logger.debug { "Not updating $event with a doctor with id $it" } }
                    }
                    .forEach { it.addEvent(event) }
            }
        }
    }

    private suspend fun updatePatients(event: Event, patients: Updatable<List<PatientDto.PatientDtoId>>) {
        when (patients) {
            is Updatable.Ignore -> {}
            is Updatable.Update -> {
                patients.value
                    .mapNotNull {
                        readPatientService.getByPatientId(it)
                            ?: doThenNull { logger.debug { "Not updating $event with a doctor with id $it" } }
                    }
                    .forEach { it.addEvent(event) }
            }
        }
    }

    private fun updateIcal(event: Event, ical: Updatable<String>) {
        when (ical) {
            is Updatable.Ignore -> {}
            is Updatable.Update -> {
                event.ical = ical.value
            }
        }
    }
}