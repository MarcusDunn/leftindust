package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.PersistenceError
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.survey.complete.CompleteSurvey
import dev.forkhandles.result4k.Result4k

interface UpdatePatientService {
    suspend fun addDoctorToPatient(pid: PatientDto.PatientDtoId, did: DoctorDto.DoctorDtoId): Patient

    suspend fun update(patientInput: UpdatePatient): Result4k<Patient, PersistenceError>
}

