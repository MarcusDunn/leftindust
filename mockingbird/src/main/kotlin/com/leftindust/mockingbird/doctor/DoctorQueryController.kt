package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorQueryController(
    private val readDoctorService: ReadDoctorService,
    private val doctorToDoctorDtoConverter: InfallibleConverter<Doctor, DoctorDto>,
) {
    private val logger = LoggerFactory.getLogger(DoctorQueryController::class.java)

    @QueryMapping
    suspend fun doctorsById(@Argument doctorIds: Flow<DoctorDto.DoctorDtoId>): Flow<DoctorDto?> {
        return doctorIds
            .map { readDoctorService.getByDoctorId(it) }
            .map { doctor -> doctor?.let { doctorToDoctorDtoConverter.convert(it) } }
    }

    @QueryMapping
    suspend fun doctorsByPatientId(@Argument patientId: PatientDto.PatientDtoId): Flow<DoctorDto>? {
        return readDoctorService
            .getByPatientId(patientId)
            ?.map { doctorToDoctorDtoConverter.convert(it) }
    }

    @QueryMapping
    suspend fun doctorsByRange(@Argument range: RangeDto): Flow<DoctorDto> {
        return readDoctorService
            .getMany(range)
            .map { doctorToDoctorDtoConverter.convert(it) }
    }

    @QueryMapping
    suspend fun doctorsByExample(@Argument example: GraphQLDoctorExample): Flow<DoctorDto> {
        return readDoctorService
            .searchByExample(example)
            .map { doctorToDoctorDtoConverter.convert(it) }
    }
}

