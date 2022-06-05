package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorPatientsQueryController(
    private val patientService: ReadPatientService,
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>,
) {
    @SchemaMapping
    suspend fun patients(doctorDto: DoctorDto): Flow<PatientDto> {
        val patientFlow = patientService.getByDoctor(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadPatientService::getByDoctor)
        return patientFlow.map { patientToPatientDtoConverter.convert(it) }
    }
}