package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.patient.toPatientDto
import com.leftindust.mockingbird.visit.ReadVisitService
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.visit.VisitDto
import dev.forkhandles.result4k.onFailure
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class EventQueryController(
    private val readEventService: ReadEventService,
) {
    private val logger = KotlinLogging.logger { }

    @QueryMapping
    suspend fun eventsByIds(events: List<EventDto.EventDtoId>): List<EventDto?> {
        return events
            .map { readEventService.getByEventId(it) }
            .map { it?.let { event -> event.toEventDto().onFailure { throw it.reason.toMockingbirdException() } } }
    }

    @QueryMapping
    suspend fun eventsByDoctorId(@Argument doctorId: DoctorDto.DoctorDtoId): List<EventDto>? {
        val events = readEventService.getByDoctorId(doctorId) ?: return null
        return events.map { it.toEventDto().onFailure { throw it.reason.toMockingbirdException() } }
    }

    @QueryMapping
    suspend fun eventsByPatientId(@Argument patientId: PatientDto.PatientDtoId): List<EventDto>? {
        val events = readEventService.getByPatientId(patientId) ?: return null
        return events.map {
            it.toEventDto().onFailure { throw it.reason.toMockingbirdException() }
        }
    }

    @Controller
    class EventPatientController(
        private val readPatientService: ReadPatientService,
    ) {
        @QueryMapping
        suspend fun patients(eventDto: EventDto): List<PatientDto> {
            val patients = readPatientService.getByEvent(eventDto.id)
                ?: throw NullSubQueryException(eventDto, ReadPatientService::getByEvent)
            return patients.map { it.toPatientDto().onFailure { throw it.reason.toMockingbirdException() } }
        }
    }

    @Controller
    class EventDoctorController(
        private val readDoctorService: ReadDoctorService,
    ) {
        @QueryMapping
        suspend fun doctors(eventDto: EventDto): List<DoctorDto> {
            TODO()
        }
    }

    @Controller
    class EventVisitController(
        private val readVisitService: ReadVisitService,
        private val visitToVisitDtoConverter: InfallibleConverter<Visit, VisitDto>,
    ) {
        @QueryMapping
        suspend fun visit(eventDto: EventDto): VisitDto? {
            return readVisitService
                .findByEvent(eventDto.id)
                ?.let { visitToVisitDtoConverter.convert(it) }
        }
    }
}