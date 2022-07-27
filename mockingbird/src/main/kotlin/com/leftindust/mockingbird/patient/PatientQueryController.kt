package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class PatientQueryController(
    private val readPatientService: ReadPatientService,
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>,
) {
    private val logger = KotlinLogging.logger { }

    suspend fun patientsByRange(range: RangeDto): List<PatientDto> {
        return readPatientService.getMany(range).map { patientToPatientDtoConverter.convert(it) }
    }

    @QueryMapping("patientsByPatientId")
    suspend fun patientsByPatientId(@Argument("patientIds") patientIds: List<PatientDto.PatientDtoId>): List<PatientDto?> {
        return patientIds
            .map { readPatientService.getByPatientId(it) }
            .map { it?.let { patient -> patientToPatientDtoConverter.convert(patient) } }
    }

    suspend fun patientsByExample(example: PatientExampleDto): List<PatientDto> {
        return readPatientService.searchByExample(example).map { patientToPatientDtoConverter.convert(it) }
    }
}
