package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import dev.forkhandles.result4k.onFailure
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorQueryController(
    private val readDoctorService: ReadDoctorService,
) {
    private val logger = LoggerFactory.getLogger(DoctorQueryController::class.java)

    @QueryMapping("doctorsByDoctorIds")
    suspend fun doctorsById(@Argument("doctorIds") doctorIds: List<DoctorDto.DoctorDtoId>): List<DoctorDto?> {
        return doctorIds
            .map { readDoctorService.getByDoctorId(it) }
            .map { doctor -> doctor?.let { it.toDoctorDto().onFailure { throw it.reason.toMockingbirdException() } } }
    }

    @QueryMapping
    suspend fun doctorsByPatientId(@Argument patientId: PatientDto.PatientDtoId): List<DoctorDto>? {
        return readDoctorService
            .getByPatientId(patientId)
            ?.map { it.toDoctorDto().onFailure { throw it.reason.toMockingbirdException() } }
    }

    @QueryMapping("doctorsByRange")
    suspend fun doctorsByRange(@Argument("range") range: RangeDto): List<DoctorDto> {
        return readDoctorService
            .getMany(range)
            .map { it.toDoctorDto().onFailure { throw it.reason.toMockingbirdException() } }
    }

    @QueryMapping
    suspend fun doctorsByExample(@Argument example: GraphQLDoctorExample): List<DoctorDto> {
        return readDoctorService
            .searchByExample(example)
            .map { it.toDoctorDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}

