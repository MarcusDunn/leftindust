package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.visit.VisitDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_EVENT')")
interface ReadEventService {
    suspend fun getByEventId(eventId: EventDto.EventDtoId): Event?
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<Event>?
    suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Event>?
    suspend fun getByVisitId(visitId: VisitDto.VisitDtoId): Event?
}