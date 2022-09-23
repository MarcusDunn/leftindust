package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleDtoException
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.doctor.CreateDoctor
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class PatientMutationController(
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>,
    private val createPatientDtoToCreatePatient: FallibleConverter<CreatePatientDto, CreatePatient>,
    private val createPatientService: CreatePatientService,
) {

    suspend fun updatePatient(patient: UpdatePatientDto): PatientDto {
        TODO()
    }

    @MutationMapping
    suspend fun addPatient(@Argument("createPatient") createPatientDto: CreatePatientDto, dataFetchingEnvironment: DataFetchingEnvironment): PatientDto {
        val createPatient = createPatientDtoToCreatePatient.convert(createPatientDto)
        val newPatient = createPatientService.addNewPatient(createPatient ?: throw InconvertibleDtoException<CreatePatient>(createPatientDto))
        return patientToPatientDtoConverter.convert(newPatient)
    }
}