package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto

interface UpdatePatientService {
    suspend fun addDoctorToPatient(pid: PatientDto.PatientDtoId, did: DoctorDto.DoctorDtoId): Patient

    suspend fun update(patientInput: UpdatePatientDto): Patient

    suspend fun assignForms(patients: List<PatientDto.PatientDtoId>, survey: SurveyDto.SurveyDtoId): List<Patient>
}

