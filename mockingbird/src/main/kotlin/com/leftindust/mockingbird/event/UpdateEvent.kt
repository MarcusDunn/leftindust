package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.PatientDto

interface UpdateEvent {
    val eid: EventDto.EventDtoId
    val ical: Updatable<String>
    val doctors: Updatable<List<DoctorDto.DoctorDtoId>>
    val patients: Updatable<List<PatientDto.PatientDtoId>>
}