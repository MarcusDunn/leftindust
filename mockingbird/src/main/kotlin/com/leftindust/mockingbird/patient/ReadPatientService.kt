package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.visit.VisitDto
import kotlinx.coroutines.flow.Flow

interface ReadPatientService {
    suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Patient?
    suspend fun getByDoctor(doctorId: DoctorDto.DoctorDtoId): Flow<Patient>?
    suspend fun getVisitPatients(visitId: VisitDto.VisitDtoId): Flow<Patient>
    suspend fun getMany(range: Range): Flow<Patient>
    suspend fun getByEvent(eventId: EventDto.EventDtoId): Flow<Patient>?
    suspend fun searchByExample(example: Example<Patient>): Flow<Patient>
    suspend fun getByUser(uid: String): Patient?
}