package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.stereotype.Service

@Service
class ReadVisitServiceImpl : ReadVisitService {
    override fun getByVisitId(vid: VisitDto.VisitDtoId): Visit? {
        TODO("Not yet implemented")
    }

    override fun findByEvent(eid: EventDto.EventDtoId): Visit? {
        TODO("Not yet implemented")
    }

    override fun getPatientVisits(pid: PatientDto.PatientDtoId): List<Visit> {
        TODO("Not yet implemented")
    }
}
