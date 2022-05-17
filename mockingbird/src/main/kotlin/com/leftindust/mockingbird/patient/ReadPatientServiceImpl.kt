package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.visit.VisitDto
import javax.transaction.Transactional
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadPatientServiceImpl : ReadPatientService {
    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Patient? {
        TODO("Not yet implemented")
    }

    override suspend fun getByDoctor(doctorId: DoctorDto.DoctorDtoId): Flow<Patient>? {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitPatients(visitId: VisitDto.VisitDtoId): Flow<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(range: Range): Flow<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getByEvent(eventId: EventDto.EventDtoId): Flow<Patient>? {
        TODO("Not yet implemented")
    }

    override suspend fun searchByExample(example: Example<Patient>): Flow<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getByUser(uid: String): Patient? {
        TODO("Not yet implemented")
    }
}