package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.applyUpdatable
import com.leftindust.mockingbird.graphql.types.applyUpdatableGqlId
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateEventServiceImpl(
    private val eventRepository: HibernateEventRepository,
    private val doctorRepository: DoctorRepository,
    private val readPatientService: ReadPatientService,
) : UpdateEventService {
    private val logger = KotlinLogging.logger { }

    override suspend fun updateEvent(updateEvent: UpdateEvent): Event? {
        val event = eventRepository.findById(updateEvent.eid.value).orElse(null) ?: return null
        updateIcal(event, updateEvent.ical)
        updatePatients(event, updateEvent.patients)
        updateDoctors(event, updateEvent.doctors)
        return eventRepository.save(event)
    }

    private suspend fun updateDoctors(event: Event, doctors: Updatable<List<DoctorDto.DoctorDtoId>>) {
        doctors.applyUpdatableGqlId(
            entity = event,
            property = event::doctors,
            addToCollection = { doctorId -> doctorRepository.findByIdOrNull(doctorId.value)?.addEvent(event) },
            logger = logger
        )
    }

    private suspend fun updatePatients(event: Event, patients: Updatable<List<PatientDto.PatientDtoId>>) {
        patients.applyUpdatableGqlId(
            entity = event,
            property = event::patients,
            addToCollection = { patientDtoId -> readPatientService.getByPatientId(patientDtoId)?.addEvent(event) },
            logger = logger
        )
    }

    private fun updateIcal(event: Event, ical: Updatable<String>) {
        ical.applyUpdatable(event, event::ical, logger)
    }
}