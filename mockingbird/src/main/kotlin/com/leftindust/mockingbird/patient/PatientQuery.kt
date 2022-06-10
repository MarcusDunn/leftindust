package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.survey.SurveyDto
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.visit.VisitDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Controller

@Controller
class PatientQuery(
    private val readPatientService: ReadPatientService,
    private val patientToPatientDtoConverter: InfallibleConverter<Patient, PatientDto>,
) {
    private val logger = KotlinLogging.logger { }

    suspend fun patientsByRange(range: RangeDto): List<PatientDto> {
        return readPatientService.getMany(range).map { patientToPatientDtoConverter.convert(it) }
    }

    suspend fun patientsByPatientId(patientIds: List<PatientDto.PatientDtoId>): List<PatientDto?> {
        return patientIds
            .map { readPatientService.getByPatientId(it) }
            .map { it?.let { patient -> patientToPatientDtoConverter.convert(patient) } }
    }

    suspend fun patientsByExample(example: PatientExampleDto): List<PatientDto> {
        return readPatientService.searchByExample(example).map { patientToPatientDtoConverter.convert(it) }
    }

    suspend fun contacts(): List<ContactDto> = TODO()

    suspend fun doctors(): List<DoctorDto> = TODO()

    suspend fun visits(): List<VisitDto> = TODO()

    suspend fun user(): MediqUserDto? = TODO()

    suspend fun events(): List<EventDto> = TODO()

    suspend fun assignedSurveys(): List<SurveyDto> = TODO()

    suspend fun phones(): List<PhoneDto> = TODO()

    suspend fun emails(): List<EmailDto> = TODO()

    suspend fun addresses(): List<AddressDto> = TODO()
}
