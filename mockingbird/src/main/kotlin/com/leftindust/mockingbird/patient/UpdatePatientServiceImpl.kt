package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.doctor.DoctorDto
import org.springframework.stereotype.Service


@Service
class UpdatePatientServiceImpl : UpdatePatientService {
    override suspend fun addDoctorToPatient(pid: PatientDto.PatientDtoId, did: DoctorDto.DoctorDtoId): Patient {
        TODO("Not yet implemented")
    }

    override suspend fun update(patientInput: UpdatePatientDto): Patient {
        TODO("Not yet implemented")
    }
}