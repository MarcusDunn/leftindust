package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.*
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.*
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.applyDeletable
import com.leftindust.mockingbird.graphql.types.ignore
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.ReadMediqUserService
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
@Transactional
class UpdateDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val readMediqUserService: ReadMediqUserService,
    private val updateNameInfoService: UpdateNameInfoService,
    private val createPhoneService: CreatePhoneService,
    private val readClinicService: ReadClinicService,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val updateClinicService: UpdateClinicService,
    private val readDoctorService: ReadDoctorService,
) : UpdateDoctorService {
    private val logger = KotlinLogging.logger { }

    override suspend fun editDoctor(updateDoctor: UpdateDoctor): Result4k<Doctor, IntoMockingbirdException> {
        val doctor = doctorRepository.findByIdOrNull(updateDoctor.did.value)
            ?: run {
                logger.warn { NoUpdatesOccurredNoEntityWithId(DoctorEntity::class, updateDoctor.did.value) }
                return PersistenceError.FindError.invoke(
                    DoctorEntity::class,
                    updateDoctor.did.value
                )
            }

        updateUserUid(updateDoctor.userUid, doctor)
        updateNameInfo(updateDoctor.nameInfo, doctor)
        updatePhones(updateDoctor.phones, doctor)
        updateDoctor.title.applyDeletable(doctor, doctor::title, logger)
        updateClinics(updateDoctor.clinics, doctor).onFailure { return it }
        updateDoctor.dateOfBirth.applyDeletable(doctor, doctor::dateOfBirth, logger)
        updateAddresses(updateDoctor.addresses, doctor)
        updateEmails(updateDoctor.emails, doctor)
        updatePatients(updateDoctor.patients, doctor)
        return Success(doctorRepository.save(doctor).toDoctor().onFailure { throw it.reason.toMockingbirdException() })
    }

    private suspend fun updatePatients(patients: Updatable<List<PatientDto.PatientDtoId>>, doctor: DoctorEntity) {
        when (patients) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::patients) }
            }

            is Updatable.Update -> {
                doctor.clearPatients()
                patients.value
                    .map { it to patientRepository.findByIdOrNull(it.value) }
                    .forEach { (id, patient) ->
                        patient
                            ?.let { doctor.addPatient(patient) }
                            ?: logger.warn { MissedCollectionAddNoEntityWithId(doctor, doctor::patients, id.value) }
                    }
            }
        }
    }

    private suspend fun updateEmails(emails: Updatable<List<CreateEmail>>, doctor: DoctorEntity) {
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

    private suspend fun updateAddresses(addresses: Updatable<List<CreateAddress>>, doctor: DoctorEntity) {
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

    private suspend fun updateClinics(
        clinics: Updatable<List<ClinicDto.ClinicDtoId>>,
        doctor: DoctorEntity
    ): Result4k<Unit, IntoMockingbirdException> {
        when (clinics) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::clinics) }
            }

            is Updatable.Update -> {
                val newDoctorId = DoctorDto.DoctorDtoId(doctor.id!!)
                doctor.clinics.forEach { it.clinic.removeDoctor(doctor) }


                clinics.value
                    .map { clinicId ->
                        readClinicService.getByClinicId(clinicId)
                            ?: return PersistenceError.FindError.invoke(ClinicEntity::class, clinicId.value)
                        clinicId
                    }.map { id ->
                        val doctorIds =
                            readDoctorService.getByClinicId(id)?.let { it.map { id -> DoctorDto.DoctorDtoId(id.id) } }
                                ?: run {
                                    logger.warn { MissedCollectionAddNoEntityWithId(doctor, doctor::clinics, id.value) }
                                    return PersistenceError.FindError.invoke(DoctorEntity::class, id.value)
                                }

                        val editClinic = ClinicEditImpl(
                            id,
                            ignore(),
                            ignore(),
                            Updatable.Update(listOf(newDoctorId, *doctorIds.toTypedArray()))
                        )
                        updateClinicService.editClinic(editClinic)
                    }
                // TODO: Add the clinic to the doctor. Clinic cannot currently be converted to ClinicEntity for ClinicDoctorEntity
            }
        }
        return Success(Unit)
    }

    private fun updateTitle(title: Deletable<String>, doctor: DoctorEntity) {
        title.applyDeletable(doctor, doctor::title, logger)
    }

    private suspend fun updatePhones(phones: Updatable<List<CreatePhone>>, doctor: DoctorEntity) {
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

    private suspend fun updateNameInfo(nameInfo: Updatable<UpdateNameInfo>, doctor: DoctorEntity) {
        when (nameInfo) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::nameInfoEntity) }
            }

            is Updatable.Update -> {
                updateNameInfoService.updateNameInfo(nameInfo.value, doctor.nameInfoEntity)
            }
        }
    }


    private suspend fun updateUserUid(userUid: Deletable<String>, doctor: DoctorEntity) {
        when (userUid) {
            is Deletable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(doctor, doctor::user) }
            }

            is Deletable.Update -> {
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