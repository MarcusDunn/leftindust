package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto

data class CreateEventDto(
    override val iCal: String,
    override val doctors: List<DoctorDto.DoctorDtoId>,
    override val patients: List<PatientDto.PatientDtoId>,
) : CreateEvent

