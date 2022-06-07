package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.visit.VisitDto
import java.time.Period
import java.time.ZonedDateTime
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_EVENT')")
interface ReadEventService {
    suspend fun getByEventId(eventId: EventDto.EventDtoId): Event?
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Event>?
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Flow<Event>?
    suspend fun getByVisitId(visitId: VisitDto.VisitDtoId): Event?
}