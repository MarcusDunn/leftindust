package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.input.Range
import com.leftindust.mockingbird.graphql.types.input.toPageable
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.visit.VisitDto
import javax.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadPatientServiceImpl(
    private val patientRepository: PatientRepository
) : ReadPatientService {
    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Patient? {
        return patientRepository.findByIdOrNull(patientId.value)
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Patient>? {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitPatients(visitId: VisitDto.VisitDtoId): List<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(range: Range): List<Patient> {
        return patientRepository.findAll(range.toPageable(Sort.by(Patient_.ID))).toList()
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