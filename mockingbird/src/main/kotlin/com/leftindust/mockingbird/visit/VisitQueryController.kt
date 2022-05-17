package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.stereotype.Controller

@Controller
class VisitQueryController(
    private val readVisitService: ReadVisitService,
) {

    fun visitsByDoctor(did: DoctorDto.DoctorDtoId) {
        TODO()
    }

    fun visitsByPatient(pid: PatientDto.PatientDtoId) {
        TODO()
    }

    suspend fun visitByPatientOrDoctor(pid: PatientDto.PatientDtoId, did: DoctorDto.DoctorDtoId): List<VisitDto> {
        TODO()
    }

    fun visitsById(vids: List<VisitDto.VisitDtoId>) {
        TODO()
    }
}