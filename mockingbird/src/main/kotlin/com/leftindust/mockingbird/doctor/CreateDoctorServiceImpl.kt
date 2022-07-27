package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.ReadClinicService
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.CreateUserDto
import com.leftindust.mockingbird.user.CreateUserService
import com.leftindust.mockingbird.user.ReadUserService
import javax.transaction.Transactional
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
    private val readClinicService: ReadClinicService,
    private val readPatientService: ReadPatientService,
) : CreateDoctorService {

    override suspend fun addDoctor(createDoctor: CreateDoctor): Doctor {
        val (user, nameInfo) = when (val user = createDoctor.user) {
            is CreateDoctor.User.Create -> createUserService.addUser(CreateUserDto(user.uid, user.nameInfo, user.group, null)) to null
            is CreateDoctor.User.Find -> readUserService.findUserByUid(user.userUid) to null
            is CreateDoctor.User.NoUser -> null to createNameInfoService.createNameInfo(user.nameInfo)
        }
        val notNullNameInfo = nameInfo ?: user?.nameInfo!!
        val doctor = Doctor(
            nameInfo = notNullNameInfo,
            addresses = createDoctor.addresses.map { createAddressService.createAddress(it) }.toMutableSet(),
            emails = createDoctor.emails.map { createEmailService.createEmail(it) }.toMutableSet(),
            phones = createDoctor.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            user = user,
            events = mutableSetOf(), // not currently possible to set on create doctor.
            thumbnail = null,
            title = createDoctor.title,
            dateOfBirth = createDoctor.dateOfBirth,
            clinics = mutableSetOf(), // set in apply block
            patients = mutableSetOf(), // set in apply block
        ).apply {
            createDoctor.patients
                .map {
                    readPatientService.getByPatientId(it)
                        ?: throw IllegalArgumentException("No such patient with id $it")
                }.forEach { addPatient(it) }

            createDoctor.clinic.map {
                readClinicService.getByClinicId(it)
                    ?: throw IllegalArgumentException("No such clinic with id $it")
            }.forEach { it.addDoctor(this) }
        }
        return doctorRepository.save(doctor)
    }
}