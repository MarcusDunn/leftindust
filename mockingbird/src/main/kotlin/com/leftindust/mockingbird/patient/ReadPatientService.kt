package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.visit.VisitDto
import kotlinx.coroutines.flow.Flow

interface ReadPatientService {
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Patient?
    suspend fun getByDoctor(doctorId: DoctorDto.DoctorDtoId): List<Patient>?
    suspend fun getVisitPatients(visitId: VisitDto.VisitDtoId): List<Patient>
    suspend fun getMany(range: Range): List<Patient>
    suspend fun getByEvent(eventId: EventDto.EventDtoId): List<Patient>?
    suspend fun searchByExample(example: Example<Patient>): List<Patient>
    suspend fun getByUser(uid: String): Patient?
}