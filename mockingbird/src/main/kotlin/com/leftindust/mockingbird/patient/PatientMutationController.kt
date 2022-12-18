package com.leftindust.mockingbird.patient


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.contact.CreateContactGraphQlDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.CreateEmailGraphQlDto
import com.leftindust.mockingbird.graphql.types.input.toMap
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.CreatePhoneGraphQlDto
import dev.forkhandles.result4k.onFailure
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class PatientMutationController(
    private val patientToPatientDtoConverter: InfallibleConverter<Patient?, PatientDto>,
    private val createPatientService: CreatePatientService,
    private val updatePatientService: UpdatePatientService,
) {
    @MutationMapping("editPatient")
    suspend fun editPatient(@Argument("editPatient") editPatient: ArgumentValueUpdatePatientDto): PatientDto? {
        val toUpdatePatientDto = editPatient.toUpdatePatientDto()
        val updatedPatient = updatePatientService.update(toUpdatePatientDto.toUpdatePatient().onFailure { throw it.reason.toMockingbirdException() })
        return patientToPatientDtoConverter.convert(updatedPatient.onFailure { throw it.reason.toMockingbirdException() })}

    @MutationMapping("addPatient")
    suspend fun addPatient(
        @Argument("createPatient") createPatientDto: CreatePatientDto,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): PatientDto {
        val createPatient = createPatientDto.toCreatePatient().onFailure { throw it.reason.toMockingbirdException() }
        val newPatient = createPatientService.addNewPatient(createPatient)
        return patientToPatientDtoConverter.convert(newPatient)
    }

    data class UpdatePatientGraphQlDto(
        val pid: PatientDto.PatientDtoId,
        val nameInfo: CreateNameInfoDto,
        val phones: List<CreatePhoneGraphQlDto>?,
        val dateOfBirth: LocalDate,
        val addresses: List<CreateAddressGraphQlDto>?,
        val emails: List<CreateEmailGraphQlDto>?,
        val insuranceNumber: String,
        val sex: Sex,
        val gender: String,
        val ethnicity: Ethnicity,
        val emergencyContacts: List<CreateContactGraphQlDto>?,
        val doctors: List<DoctorDto.DoctorDtoId>?,
        val thumbnail: String?,
    )
}