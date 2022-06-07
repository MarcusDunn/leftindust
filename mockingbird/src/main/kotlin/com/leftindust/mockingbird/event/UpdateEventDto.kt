package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.PatientDto

data class UpdateEventDto(
    override val eid: EventDto.EventDtoId,
    override val ical: Updatable<String>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
    override val patients: Updatable<List<PatientDto.PatientDtoId>>,
) : UpdateEvent

