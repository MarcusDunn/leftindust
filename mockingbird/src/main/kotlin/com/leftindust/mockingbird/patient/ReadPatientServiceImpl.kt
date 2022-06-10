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

    override suspend fun getByDoctor(doctorId: DoctorDto.DoctorDtoId): List<Patient>? {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitPatients(visitId: VisitDto.VisitDtoId): List<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(range: Range): List<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getByEvent(eventId: EventDto.EventDtoId): List<Patient>? {
        TODO("Not yet implemented")
    }

    override suspend fun searchByExample(example: Example<Patient>): List<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getByUser(uid: String): Patient? {
        TODO("Not yet implemented")
    }
}