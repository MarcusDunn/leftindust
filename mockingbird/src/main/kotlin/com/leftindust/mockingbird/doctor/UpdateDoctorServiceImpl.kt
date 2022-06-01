package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.LogMessage
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
    private val logger = LoggerFactory.getLogger(UpdateDoctorServiceImpl::class.java)

    override suspend fun editDoctor(updateDoctor: UpdateDoctor): Doctor? {
        val doctor = doctorRepository.findById(updateDoctor.did.value).orElse(null)
        return if (doctor == null) {
            logger.warn(LogMessage("Did not edit doctor, returning null", "No doctor with id ${updateDoctor.did} was found.").toString())
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
                logger.trace(LogMessage("Did not update $patients ${Doctor::patients.name}", "Update was $patients").toString())
            }
            is Updatable.Update -> {
                val newPatients = patients.value.map { it to readPatientService.getByPatientId(it) }
                if (newPatients.any { it.second == null }) {
                    val missingPatientIds = newPatients.filter { it.second == null }.map { it.first }
                    logger.warn(LogMessage("Did not update $doctor ${Doctor::patients.name}",
                        "${ReadPatientService::class.simpleName} ${ReadPatientService::getByPatientId.name} returned null for $missingPatientIds").toString())
                } else {
                    doctor.patients.forEach { doctor.removePatient(it.patient) }
                    newPatients.map { it.second!! }.forEach { doctor.addPatient(it) }
                    logger.trace(LogMessage("Updated $doctor ${Doctor::clinics.name} to ${patients.value}", "Update was $patients").toString())
                }
            }
        }
    }

    private suspend fun updateEmails(emails: Updatable<List<CreateEmail>>, doctor: Doctor) {
        when (emails) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::emails.name}", "Update was $emails").toString())
            }
            is Updatable.Update -> {
                val newEmails = emails.value.map { createEmailService.createEmail(it) }
                doctor.emails.clear()
                doctor.emails.addAll(newEmails)
                logger.trace(LogMessage("Updated $doctor ${Doctor::emails.name} to ${emails.value}", "Update was $emails").toString())
            }
        }
    }

    private suspend fun updateAddresses(addresses: Updatable<List<CreateAddress>>, doctor: Doctor) {
        when (addresses) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::addresses.name}", "Update was $addresses").toString())
            }
            is Updatable.Update -> {
                val newAddresses = addresses.value.map { createAddressService.createAddress(it) }
                doctor.addresses.clear()
                doctor.addresses.addAll(newAddresses)
                logger.trace(LogMessage("Updated $doctor ${Doctor::addresses.name} to ${addresses.value}", "Update was $addresses").toString())
            }
        }
    }

    private fun updateDateOfBirth(dateOfBirth: Updatable<LocalDateDto>, doctor: Doctor) {
        when (dateOfBirth) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::dateOfBirth.name}", "Update was $dateOfBirth").toString())
            }
            is Updatable.Update -> {
                val localDate = localDateDtoToLocalDateConverter.convert(dateOfBirth.value)
                doctor.dateOfBirth = localDate
                logger.trace(LogMessage("Updated $doctor ${Doctor::dateOfBirth.name} to ${dateOfBirth.value}", "Update was $dateOfBirth").toString())
            }
        }
    }

    private suspend fun updateClinics(clinics: Updatable<List<ClinicDto.ClinicDtoId>>, doctor: Doctor) {
        when (clinics) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::clinics.name}", "Update was $clinics").toString())
            }
            is Updatable.Update -> {
                val newClinics = clinics.value.map { it to readClinicService.getByClinicId(it) }
                if (newClinics.any { it.second == null }) {
                    val missingClinicIds = newClinics.filter { it.second == null }.map { it.first }
                    logger.warn(LogMessage("Did not update $doctor ${Doctor::clinics.name}",
                        "${ReadClinicService::class.simpleName} ${ReadClinicService::getByClinicId.name} returned null for $missingClinicIds").toString())
                } else {
                    doctor.clinics.forEach { it.clinic.removeDoctor(doctor) }
                    newClinics.map { it.second!! }.forEach { it.addDoctor(doctor) }
                    logger.trace(LogMessage("Updated $doctor ${Doctor::clinics.name} to ${clinics.value}", "Update was $clinics").toString())
                }
            }
        }
    }

    private fun updateTitle(title: Updatable<String>, doctor: Doctor) {
        when (title) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::title.name}", "Update was $title").toString())
            }
            is Updatable.Update -> {
                logger.trace(LogMessage("Updated $doctor ${Doctor::title.name} to ${title.value}", "Update was $title").toString())
                doctor.title = title.value
            }
        }
    }

    private suspend fun updatePhones(phones: Updatable<List<CreatePhone>>, doctor: Doctor) {
        when (phones) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::phones.name}", "Update was $phones").toString())
            }
            is Updatable.Update -> {
                val newPhones = phones.value.map { createPhoneService.createPhone(it) }
                doctor.phones.clear()
                doctor.phones.addAll(newPhones)
                logger.trace(LogMessage("Updated $doctor ${Doctor::phones.name} to ${phones.value}", "Update was $phones").toString())
            }
        }
    }

    private suspend fun updateNameInfo(nameInfo: Updatable<UpdateNameInfo>, doctor: Doctor) {
        when (nameInfo) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::nameInfo.name}", "Update was $nameInfo").toString())
            }
            is Updatable.Update -> {
                updateNameInfoService.updateNameInfo(nameInfo.value, doctor.nameInfo)
                logger.trace(LogMessage("Updated $doctor ${Doctor::nameInfo.name} to ${nameInfo.value}", "Update was $nameInfo").toString())
            }
        }
    }


    private suspend fun updateUserUid(userUid: Deletable<String>, doctor: Doctor) {
        when (userUid) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $doctor ${Doctor::user.name}", "Update was $userUid").toString())
            }
            is Updatable.Update -> {
                val user = readUserService.getByUserUid(userUid.value)
                if (user == null) {
                    logger.warn(LogMessage("Did not update $doctor ${Doctor::user.name}", "${ReadUserService::class.simpleName} ${ReadUserService::getByUserUid.name} returned $user").toString())
                } else {
                    logger.trace(LogMessage("Updated $doctor ${Doctor::user.name} to ${userUid.value}", "Update was $userUid").toString())
                    doctor.user = user
                }
            }
            is Deletable.Delete -> {
                logger.trace(LogMessage("Removing $doctor ${Doctor::user.name}", "Update was $userUid").toString())
                doctor.user = null
            }
        }
    }
}