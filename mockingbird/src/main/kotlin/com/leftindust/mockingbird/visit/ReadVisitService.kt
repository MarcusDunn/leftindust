package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_VISIT')")
interface ReadVisitService {
    fun getByVisitId(vid: VisitDto.VisitDtoId): Visit?
    fun findByEvent(eid: EventDto.EventDtoId): Visit?
    fun getPatientVisits(pid: PatientDto.PatientDtoId): Flow<Visit>
}