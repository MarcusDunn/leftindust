package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.clinic.ReadClinicService
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.LocalDateDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.ReadUserService
import java.time.LocalDate
import javax.transaction.Transactional
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val readUserService: ReadUserService,
    private val updateNameInfoService: UpdateNameInfoService,
    private val createPhoneService: CreatePhoneService,
    private val readClinicService: ReadClinicService,
    private val localDateDtoToLocalDateConverter: FallibleConverter<LocalDateDto, LocalDate>,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val readPatientService: ReadPatientService,
) : UpdateDoctorService {
    private val logger = KotlinLogging.logger {  }

    override suspend fun editDoctor(updateDoctor: UpdateDoctor): Doctor? {
        val doctor = doctorRepository.findById(updateDoctor.did.value).orElse(null)
        return if (doctor == null) {
            logger.warn { "Did not edit doctor, returning null. No doctor with id ${updateDoctor.did} was found." }
            null
        } else {
            updateUserUid(updateDoctor.userUid, doctor)
            updateNameInfo(updateDoctor.nameInfo, doctor)
            updatePhones(updateDoctor.phones, doctor)
            updateTitle(updateDoctor.title, doctor)
            updateClinics(updateDoctor.clinics, doctor)
            updateDateOfBirth(updateDoctor.dateOfBirth, doctor)
            updateAddresses(updateDoctor.addresses, doctor)
            updateEmails(updateDoctor.emails, doctor)
            updatePatients(updateDoctor.patients, doctor)
            doctorRepository.save(doctor)
        }
    }

    private suspend fun updatePatients(patients: Updatable<List<PatientDto.PatientDtoId>>, doctor: Doctor) {
        when (patients) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $patients ${Doctor::patients.name}. Update was $patients") }
            }
            is Updatable.Update -> {
                val newPatients = patients.value.map { it to readPatientService.getByPatientId(it) }
                if (newPatients.any { it.second == null }) {
                    val missingPatientIds = newPatients.filter { it.second == null }.map { it.first }
                    logger.warn { "Did not update $doctor patients. could not fina patients with ids $missingPatientIds" }
                } else {
                    doctor.patients.forEach { doctor.removePatient(it.patient) }
                    newPatients.map { it.second!! }.forEach { doctor.addPatient(it) }
                    logger.trace { ("Updated $doctor ${Doctor::clinics.name} to ${patients.value}. Update was $patients") }
                }
            }
        }
    }

    private suspend fun updateEmails(emails: Updatable<List<CreateEmail>>, doctor: Doctor) {
        when (emails) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::emails.name}. Update was $emails") }
            }
            is Updatable.Update -> {
                val newEmails = emails.value.map { createEmailService.createEmail(it) }
                doctor.emails.clear()
                doctor.emails.addAll(newEmails)
                logger.trace { ("Updated $doctor ${Doctor::emails.name} to ${emails.value}. Update was $emails") }
            }
        }
    }

    private suspend fun updateAddresses(addresses: Updatable<List<CreateAddress>>, doctor: Doctor) {
        when (addresses) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::addresses.name}. Update was $addresses") }
            }
            is Updatable.Update -> {
                val newAddresses = addresses.value.map { createAddressService.createAddress(it) }
                doctor.addresses.clear()
                doctor.addresses.addAll(newAddresses)
                logger.trace { ("Updated $doctor ${Doctor::addresses.name} to ${addresses.value}. Update was $addresses") }
            }
        }
    }

    private fun updateDateOfBirth(dateOfBirth: Updatable<LocalDateDto>, doctor: Doctor) {
        when (dateOfBirth) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::dateOfBirth.name}. Update was $dateOfBirth") }
            }
            is Updatable.Update -> {
                val localDate = localDateDtoToLocalDateConverter.convert(dateOfBirth.value)
                doctor.dateOfBirth = localDate
                logger.trace { ("Updated $doctor ${Doctor::dateOfBirth.name} to ${dateOfBirth.value}. Update was $dateOfBirth") }
            }
        }
    }

    private suspend fun updateClinics(clinics: Updatable<List<ClinicDto.ClinicDtoId>>, doctor: Doctor) {
        when (clinics) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::clinics.name}. Update was $clinics") }
            }
            is Updatable.Update -> {
                val newClinics = clinics.value.map { it to readClinicService.getByClinicId(it) }
                if (newClinics.any { it.second == null }) {
                    val missingClinicIds = newClinics.filter { it.second == null }.map { it.first }
                    logger.warn { "Did not update $doctor clinics. Could not find clinics with ids $missingClinicIds" }
                } else {
                    doctor.clinics.forEach { it.clinic.removeDoctor(doctor) }
                    newClinics.map { it.second!! }.forEach { it.addDoctor(doctor) }
                    logger.trace { ("Updated $doctor ${Doctor::clinics.name} to ${clinics.value}. Update was $clinics") }
                }
            }
        }
    }

    private fun updateTitle(title: Updatable<String>, doctor: Doctor) {
        when (title) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::title.name}. Update was $title") }
            }
            is Updatable.Update -> {
                logger.trace { ("Updated $doctor ${Doctor::title.name} to ${title.value}. Update was $title") }
                doctor.title = title.value
            }
        }
    }

    private suspend fun updatePhones(phones: Updatable<List<CreatePhone>>, doctor: Doctor) {
        when (phones) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::phones.name}. Update was $phones") }
            }
            is Updatable.Update -> {
                val newPhones = phones.value.map { createPhoneService.createPhone(it) }
                doctor.phones.clear()
                doctor.phones.addAll(newPhones)
                logger.trace { ("Updated $doctor ${Doctor::phones.name} to ${phones.value}. Update was $phones") }
            }
        }
    }

    private suspend fun updateNameInfo(nameInfo: Updatable<UpdateNameInfo>, doctor: Doctor) {
        when (nameInfo) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::nameInfo.name}. Update was $nameInfo") }
            }
            is Updatable.Update -> {
                updateNameInfoService.updateNameInfo(nameInfo.value, doctor.nameInfo)
                logger.trace { ("Updated $doctor ${Doctor::nameInfo.name} to ${nameInfo.value}. Update was $nameInfo") }
            }
        }
    }


    private suspend fun updateUserUid(userUid: Deletable<String>, doctor: Doctor) {
        when (userUid) {
            is Updatable.Ignore -> {
                logger.trace { ("Did not update $doctor ${Doctor::user.name}. Update was $userUid") }
            }
            is Updatable.Update -> {
                val user = readUserService.getByUserUid(userUid.value)
                if (user == null) {
                    logger.warn { ("Did not update $doctor user. could not find a user with id ${userUid.value}") }
                } else {
                    logger.trace { ("Updated $doctor ${Doctor::user.name} to ${userUid.value}. Update was $userUid") }
                    doctor.user = user
                }
            }
            is Deletable.Delete -> {
                logger.trace { ("Removing $doctor ${Doctor::user.name}. Update was $userUid") }
                doctor.user = null
            }
        }
    }
}