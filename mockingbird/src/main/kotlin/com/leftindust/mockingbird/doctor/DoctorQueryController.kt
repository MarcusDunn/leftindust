package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import com.leftindust.mockingbird.email.Email
import com.leftindust.mockingbird.email.EmailDto
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.event.EventFilter
import com.leftindust.mockingbird.event.EventFilterDto
import com.leftindust.mockingbird.event.FilterEventsService
import com.leftindust.mockingbird.event.ReadEventService
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.ReadUserService
import com.leftindust.mockingbird.user.MediqUserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorQueryController(
    private val readDoctorService: ReadDoctorService,
    private val doctorToDoctorDtoConverter: InfallibleConverter<Doctor, DoctorDto>,
) {
    private val logger = LoggerFactory.getLogger(DoctorQueryController::class.java)

    @QueryMapping
    suspend fun doctorsById(@Argument doctorIds: Flow<DoctorDto.DoctorDtoId>): Flow<DoctorDto?> {
        val doctors = doctorIds.map { doctorId -> doctorId to readDoctorService.getByDoctorId(doctorId) }
        return doctors.map { (doctorId, doctor) ->
            if (doctor != null) {
                doctorToDoctorDtoConverter.convert(doctor)
            } else {
                logger.trace(LogMessage("Returning a null element from ${DoctorQueryController::doctorsById.name}", "${ReadDoctorService::getByDoctorId.name} returned null for $doctorId").toString())
                null
            }
        }
    }

    @QueryMapping
    suspend fun doctorsByPatientId(@Argument patientId: PatientDto.PatientDtoId): Flow<DoctorDto>? {
        val doctorFlow = readDoctorService.getByPatientId(patientId)
        return if (doctorFlow != null) {
            doctorFlow.map { doctorToDoctorDtoConverter.convert(it) }
        } else {
            logger.trace(LogMessage("Returning null from ${DoctorQueryController::doctorsByPatientId.name}", "${ReadDoctorService::getByPatientId.name} returned null for $patientId").toString())
            null
        }
    }

    @QueryMapping
    suspend fun doctorsByRange(@Argument range: RangeDto): Flow<DoctorDto> {
        return readDoctorService.getMany(range).map { doctorToDoctorDtoConverter.convert(it) }
    }

    @QueryMapping
    suspend fun doctorsByExample(@Argument example: GraphQLDoctorExample): Flow<DoctorDto> {
        return readDoctorService.searchByExample(example).map { doctorToDoctorDtoConverter.convert(it) }
    }
}

@Controller
class DoctorEventsController(
    private val readEventsService: ReadEventService,
    private val filterEventService: FilterEventsService,
    private val eventFilterDtoToEventFilter: Converter<EventFilterDto, EventFilter>,
    private val eventToEventDtoConverter: InfallibleConverter<Event, EventDto>,
) {
    @SchemaMapping
    suspend fun events(doctorDto: DoctorDto, @Argument conditions: EventFilterDto): Flow<EventDto> {
        val events = readEventsService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadEventService::getByDoctorId)
        val eventFilter = eventFilterDtoToEventFilter.convert(conditions)
            ?: throw IllegalArgumentException("Invalid conditions $conditions")

        return filterEventService.filterEvents(events, eventFilter).map { eventToEventDtoConverter.convert(it) }
    }
}

@Controller
class DoctorPatientsController(
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

@Controller
class DoctorUserController(
    private val userService: ReadUserService,
    private val mediqUserToMediqUserDtoConverter: InfallibleConverter<MediqUser, MediqUserDto>,
) {
    @SchemaMapping
    suspend fun user(doctorDto: DoctorDto): MediqUserDto? {
        return userService.findByDoctorId(doctorDto.id)?.let { mediqUserToMediqUserDtoConverter.convert(it) }
    }
}

@Controller
class DoctorPhoneController(
    private val phoneService: ReadPhoneService,
    private val phoneToPhoneDtoConverter: InfallibleConverter<Phone, PhoneDto>,
) {
    @SchemaMapping
    suspend fun phones(doctorDto: DoctorDto): Flow<PhoneDto> {
        val phoneFlow = phoneService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadPhoneService::getByDoctorId)
        return phoneFlow.map { phoneToPhoneDtoConverter.convert(it) }
    }
}

@Controller
class DoctorEmailController(
    private val readEmailService: ReadEmailService,
    private val emailToEmailDtoConverter: InfallibleConverter<Email, EmailDto>,
) {
    @SchemaMapping
    suspend fun emails(doctorDto: DoctorDto): Flow<EmailDto> {
        val emailFlow = readEmailService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadEmailService::getByDoctorId)
        return emailFlow.map { emailToEmailDtoConverter.convert(it) }
    }
}

@Controller
class DoctorAddressesService(
    private val readAddressService: ReadAddressService,
    private val addressToAddressDtoConverter: InfallibleConverter<Address, AddressDto>,
) {
    @SchemaMapping
    suspend fun addresses(doctorDto: DoctorDto): Flow<AddressDto> {
        val addressFlow = readAddressService.getByDoctorId(doctorDto.id)
            ?: throw NullSubQueryException(doctorDto, ReadAddressService::getByDoctorId)
        return addressFlow.map { addressToAddressDtoConverter.convert(it) }
    }
}