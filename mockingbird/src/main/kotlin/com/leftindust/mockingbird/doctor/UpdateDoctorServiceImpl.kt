package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.AddedAllEntityCollectionMessage
import com.leftindust.mockingbird.ClearedEntityCollectionMessage
import com.leftindust.mockingbird.MissedCollectionAddNoEntityWithId
import com.leftindust.mockingbird.NoOpUpdatedEntityFieldMessage
import com.leftindust.mockingbird.NoUpdatesOccurredNoEntityWithId
import com.leftindust.mockingbird.SetEntityFieldMessage
import com.leftindust.mockingbird.SetToNullEntityFieldMessage
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.clinic.ClinicEditDto
import com.leftindust.mockingbird.clinic.ReadClinicService
import com.leftindust.mockingbird.clinic.UpdateClinicService
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.applyDeletable
import com.leftindust.mockingbird.graphql.types.ignore
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.ReadMediqUserService
import java.time.LocalDate
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service


@Service
@Transactional
class UpdateDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val readMediqUserService: ReadMediqUserService,
    private val updateNameInfoService: UpdateNameInfoService,
    private val createPhoneService: CreatePhoneService,
    private val readClinicService: ReadClinicService,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val readPatientService: ReadPatientService,
    private val updateClinicService: UpdateClinicService,
    private val readDoctorService: ReadDoctorService
) : UpdateDoctorService {
    private val logger = KotlinLogging.logger { }

    override suspend fun editDoctor(updateDoctor: UpdateDoctor): Doctor? {
        val doctor = doctorRepository.findById(updateDoctor.did.value).orElse(null)
            ?: run {
                logger.warn { NoUpdatesOccurredNoEntityWithId(Doctor::class, updateDoctor.did.value) }
                return null
            }

        updateUserUid(updateDoctor.userUid, doctor)
        updateNameInfo(updateDoctor.nameInfo, doctor)
        updatePhones(updateDoctor.phones, doctor)
        updateTitle(updateDoctor.title, doctor)
        updateClinics(updateDoctor.clinics, doctor)
        updateDateOfBirth(updateDoctor.dateOfBirth, doctor)
        updateAddresses(updateDoctor.addresses, doctor)
        updateEmails(updateDoctor.emails, doctor)
        updatePatients(updateDoctor.patients, doctor)
        return doctorRepository.save(doctor)
    }

    private suspend fun updatePatients(patients: Updatable<List<PatientDto.PatientDtoId>>, doctor: Doctor) {
        when (patients) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::patients) }
            }
            is Updatable.Update -> {
                doctor.clearPatients()
                patients.value
                    .map { it to readPatientService.getByPatientId(it) }
                    .forEach { (id, patient) ->
                        patient
                            ?.let { doctor.addPatient(it) }
                            ?: logger.warn { MissedCollectionAddNoEntityWithId(doctor, doctor::patients, id.value) }
                    }
            }
        }
    }

    private suspend fun updateEmails(emails: Updatable<List<CreateEmail>>, doctor: Doctor) {
        when (emails) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::emails) }
            }
            is Updatable.Update -> {
                val newEmails = emails.value.map { createEmailService.createEmail(it) }
                doctor.emails.clear()
                logger.trace { ClearedEntityCollectionMessage(doctor, doctor::emails) }
                doctor.emails.addAll(newEmails)
                logger.trace { AddedAllEntityCollectionMessage(doctor, doctor::emails, newEmails) }
            }
        }
    }

    private suspend fun updateAddresses(addresses: Updatable<List<CreateAddress>>, doctor: Doctor) {
        when (addresses) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::addresses) }
            }
            is Updatable.Update -> {
                val newAddresses = addresses.value.map { createAddressService.createAddress(it) }
                doctor.addresses.clear()
                logger.trace { ClearedEntityCollectionMessage(doctor, doctor::addresses) }
                doctor.addresses.addAll(newAddresses)
                logger.trace { AddedAllEntityCollectionMessage(doctor, doctor::addresses, newAddresses) }
            }
        }
    }

    private fun updateDateOfBirth(dateOfBirth: Updatable<LocalDate>, doctor: Doctor) {
        dateOfBirth.applyDeletable(doctor, doctor::dateOfBirth, logger)
    }

    private suspend fun updateClinics(clinics: Updatable<List<ClinicDto.ClinicDtoId>>, doctor: Doctor) {
        when (clinics) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::clinics) }
            }
            is Updatable.Update -> {
                val newDoctorId = DoctorDto.DoctorDtoId(doctor.id ?: return)
                doctor.clinics.forEach { it.clinic.removeDoctor(doctor) }
                clinics.value
                    .map { clinicId ->
                        readClinicService.getByClinicId(clinicId) ?: throw IllegalArgumentException("No such clinic with id $clinicId")
                        clinicId
                    }
                    .forEach { id ->
                        val doctorIds = readDoctorService.getByClinicId(id)
                            ?.mapNotNull { it.id }
                            ?.map { DoctorDto.DoctorDtoId(it) }

                        if (doctorIds != null) {
                            val editClinic = ClinicEditDto(
                                id,
                                ignore(),
                                ignore(),
                                Updatable.Update(listOf(newDoctorId, *doctorIds.toTypedArray())))
                            updateClinicService.editClinic(editClinic)

                            TODO("Add the clinic to the doctor. Clinic cannot currently be converted to ClinicEntity for ClinicDoctorEntity")
                        } else {
                            logger.warn { MissedCollectionAddNoEntityWithId(doctor, doctor::clinics, id.value) }
                        }
                    }
            }
        }
    }

    private fun updateTitle(title: Deletable<String>, doctor: Doctor) {
        title.applyDeletable(doctor, doctor::title, logger)
    }

    private suspend fun updatePhones(phones: Updatable<List<CreatePhone>>, doctor: Doctor) {
        when (phones) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::phones) }
            }
            is Updatable.Update -> {
                val newPhones = phones.value.map { createPhoneService.createPhone(it) }
                doctor.phones.clear()
                logger.trace { ClearedEntityCollectionMessage(doctor, doctor::phones) }
                doctor.phones.addAll(newPhones)
                logger.trace { AddedAllEntityCollectionMessage(doctor, doctor::phones, newPhones) }
            }
        }
    }

    private suspend fun updateNameInfo(nameInfo: Updatable<UpdateNameInfo>, doctor: Doctor) {
        when (nameInfo) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::nameInfo) }
            }
            is Updatable.Update -> {
                updateNameInfoService.updateNameInfo(nameInfo.value, doctor.nameInfo)
            }
        }
    }


    private suspend fun updateUserUid(userUid: Deletable<String>, doctor: Doctor) {
        when (userUid) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::user) }
            }
            is Updatable.Update -> {
                val user = readMediqUserService.getByUserUid(userUid.value)
                if (user == null) {
                    logger.warn { NoOpUpdatedEntityFieldMessage(doctor, doctor::user, "no user with id $userUid") }
                } else {
                    logger.trace { SetEntityFieldMessage(doctor, doctor::user, user) }
                    doctor.user = user
                }
            }
            is Deletable.Delete -> {
                logger.trace { SetToNullEntityFieldMessage(doctor, doctor::user) }
                doctor.user = null
            }
        }
    }
}