package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto

interface CreateEvent {
    val iCal: String
    val doctors: List<DoctorDto.DoctorDtoId>
    val patients: List<PatientDto.PatientDtoId>
}