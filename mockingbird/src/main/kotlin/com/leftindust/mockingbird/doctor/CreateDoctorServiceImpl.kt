package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.ReadClinicService
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.CreateUserDto
import com.leftindust.mockingbird.user.CreateUserService
import com.leftindust.mockingbird.user.ReadUserService
import java.time.LocalDate
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val createUserService: CreateUserService,
    private val readUserService: ReadUserService,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val createNameInfoService: CreateNameInfoService,
    private val createPhoneService: CreatePhoneService,
    private val localDateDtoToLocalDateConverter: Converter<LocalDateDto, LocalDate>,
    private val readClinicService: ReadClinicService,
    private val readPatientService: ReadPatientService,
) : CreateDoctorService {
    private val logger = LoggerFactory.getLogger(CreateDoctorServiceImpl::class.java)

    override suspend fun addDoctor(createDoctor: CreateDoctor): Doctor {
        val (user, nameInfo) = when (val user = createDoctor.user) {
            is CreateDoctor.User.Create -> createUserService.addUser(CreateUserDto(user.uid, user.nameInfo, user.group, null)) to null
            is CreateDoctor.User.Find -> readUserService.findUserByUid(user.userUid) to null
            is CreateDoctor.User.NoUser -> null to createNameInfoService.createNameInfo(user.nameInfo)
        }
        val notNullNameInfo = nameInfo ?: user?.nameInfo!!
        val doctor = Doctor(
            nameInfo = notNullNameInfo,
            addresses = createDoctor.addresses?.map { createAddressService.createAddress(it) }?.toMutableSet() ?: mutableSetOf(),
            emails = createDoctor.emails?.map { createEmailService.createEmail(it) }?.toMutableSet() ?: mutableSetOf(),
            phones = createDoctor.phones?.map { createPhoneService.createPhone(it) }?.toMutableSet() ?: mutableSetOf(),
            user = user,
            events = mutableSetOf(), // not currently possible to set on create doctor.
            thumbnail = null,
            title = createDoctor.title,
            dateOfBirth = createDoctor.dateOfBirth?.let {
                localDateDtoToLocalDateConverter.convert(it) ?: run {
                    logger.warn(LogMessage("Set the doctor ${notNullNameInfo.firstName} ${notNullNameInfo.lastName}'s date of birth to null",
                        "$localDateDtoToLocalDateConverter failed to convert $it to a LocalDate").toString())
                    null
                }
            },
            clinics = mutableSetOf(), // set in apply block
            patients = mutableSetOf(), // set in apply block
        ).apply {
            val patients = createDoctor.patients?.map { it to readPatientService.getByPatientId(it) } ?: emptyList()
            if (patients.any { it.second == null }) {
                logger.warn(LogMessage("Did not add ${patients.filter { it.second == null }.map { it.first }} to the doctor ${notNullNameInfo.firstName} ${notNullNameInfo.lastName}",
                    "${ReadPatientService::class.simpleName}.${ReadPatientService::getByPatientId.name} returned null for those patientntId(s)").toString())
            }
            patients.mapNotNull { it.second }.forEach { addPatient(it) }

            val clinics = createDoctor.clinic.map { it to readClinicService.getByClinicId(it) }
            if (clinics.any { it.second == null }) {
                logger.warn(LogMessage("Did not add ${clinics.filter { it.second == null }.map { it.first }} to the doctor ${notNullNameInfo.firstName} ${notNullNameInfo.lastName}",
                    "${ReadPatientService::class.simpleName}.${ReadClinicService::getByClinicId.name} returned null for those clinicId(s)").toString())
            }
            clinics.mapNotNull { it.second }.forEach { it.addDoctor(this) }
        }
        return doctorRepository.save(doctor)
    }
}