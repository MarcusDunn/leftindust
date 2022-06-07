package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.survey.SurveyDto
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service


@Service
class UpdatePatientServiceImpl : UpdatePatientService {
    override suspend fun addDoctorToPatient(pid: PatientDto.PatientDtoId, did: DoctorDto.DoctorDtoId): Patient {
        TODO("Not yet implemented")
    }

    override suspend fun update(patientInput: UpdatePatientDto): Patient {
        TODO("Not yet implemented")
    }

    override suspend fun assignForms(patients: List<PatientDto.PatientDtoId>, survey: SurveyDto.SurveyDtoId): Flow<Patient> {
        TODO("Not yet implemented")
    }
}