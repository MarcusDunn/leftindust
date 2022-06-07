package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.extensions.doThenNull
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.visit.ReadVisitService
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.visit.VisitDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class EventQueryController(
    private val readEventService: ReadEventService,
    private val eventToEventDtoConverter: InfallibleConverter<Event, EventDto>,
) {
    private val logger = KotlinLogging.logger { }

    @QueryMapping
    suspend fun eventsByIds(events: Flow<EventDto.EventDtoId>): Flow<EventDto?> {
        return events
            .map { readEventService.getByEventId(it) ?: doThenNull { logger.debug { "returning a null element from eventsByIds for $it" } } }
            .map { it?.let { event -> eventToEventDtoConverter.convert(event) } }
    }

    @QueryMapping
    suspend fun eventsByDoctorId(@Argument doctorId: DoctorDto.DoctorDtoId): Flow<EventDto>? {
        return readEventService.getByDoctorId(doctorId)
            ?.map { eventToEventDtoConverter.convert(it) }
            ?: doThenNull { logger.debug { "returning null from eventsByDoctorId for $doctorId" } }
    }

    @QueryMapping
    suspend fun eventsByPatientId(@Argument patientId: PatientDto.PatientDtoId): Flow<EventDto>? {
        return readEventService
            .getByPatientId(patientId)
            ?.map { eventToEventDtoConverter.convert(it) }
            ?: doThenNull { logger.debug { "returning null from eventByPatientId for $patientId" } }
    }
}

@Controller
class EventPatientController(
    private val readPatientService: ReadPatientService,
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>,
) {
    @QueryMapping
    suspend fun patients(eventDto: EventDto): Flow<PatientDto> {
        return readPatientService
            .getByEvent(eventDto.id)
            ?.map { patientToPatientDtoConverter.convert(it) }
            ?: throw NullSubQueryException(eventDto, ReadPatientService::getByEvent)
    }
}

@Controller
class EventDoctorController(
    private val readDoctorService: ReadDoctorService,
    private val doctorToDoctorDtoConverter: InfallibleConverter<Doctor, DoctorDto>,
) {
    @QueryMapping
    suspend fun doctors(eventDto: EventDto): Flow<DoctorDto> {
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