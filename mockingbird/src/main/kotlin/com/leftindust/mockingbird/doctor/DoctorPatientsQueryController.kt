package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.patient.toPatientDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorPatientsQueryController(
    private val patientService: ReadPatientService,
) {
    @SchemaMapping(field = "patients", typeName = DoctorDto.GRAPHQL_TYPE)
    suspend fun patients(doctorDto: DoctorDto): List<PatientDto> {
        val patients = patientService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadPatientService::getByDoctorId)
        return patients.map { it.toPatientDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}