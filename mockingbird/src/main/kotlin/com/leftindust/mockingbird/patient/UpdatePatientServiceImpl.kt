package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.AddedAllEntityCollectionMessage
import com.leftindust.mockingbird.ClearedEntityCollectionMessage
import com.leftindust.mockingbird.MissedCollectionAddNoEntityWithId
import com.leftindust.mockingbird.NoOpUpdatedEntityFieldMessage
import com.leftindust.mockingbird.NoUpdatesOccurredNoEntityWithId
import com.leftindust.mockingbird.PersistenceError
import com.leftindust.mockingbird.SetToNullEntityFieldMessage
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.contact.CreateContact
import com.leftindust.mockingbird.contact.CreateContactService
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.applyDeletable
import com.leftindust.mockingbird.graphql.types.applyUpdatable
import com.leftindust.mockingbird.person.UpdateNameInfo
import com.leftindust.mockingbird.person.UpdateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneService
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import java.util.Base64
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class UpdatePatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val updateNameInfoService: UpdateNameInfoService,
    private val patientEntityToPatientConverter: PatientEntityToPatientConverter,
    private val createEmailService: CreateEmailService,
    private val createAddressService: CreateAddressService,
    private val createPhoneService: CreatePhoneService,
    private val createContactService: CreateContactService,
    private val doctorRepository: DoctorRepository,
) : UpdatePatientService {

    private val logger = KotlinLogging.logger { }


    override suspend fun addDoctorToPatient(pid: PatientDto.PatientDtoId, did: DoctorDto.DoctorDtoId): Patient {
        TODO("Not yet implemented")
    }

    override suspend fun update(patientInput: UpdatePatient): Patient? {
        val patient = patientRepository.findById(patientInput.pid.value).orElse(null)
            ?: run {
                logger.warn { NoUpdatesOccurredNoEntityWithId(PatientEntity::class, patientInput.pid.value) }
                return null
            }

        patientInput.dateOfBirth.applyUpdatable(patient, patient::dateOfBirth, logger)
        patientInput.ethnicity.applyDeletable(patient, patient::ethnicity, logger)
        patientInput.gender.applyDeletable(patient, patient::gender, logger)
        patientInput.sex.applyUpdatable(patient, patient::sex, logger)
        patientInput.insuranceNumber.applyDeletable(patient, patient::insuranceNumber, logger)


        updateNameInfo(patientInput.nameInfo, patient)
        updateEmails(patientInput.emails, patient)
        updateAddresses(patientInput.addresses, patient)
        updatePhones(patientInput.phones, patient)
        updateEmergencyContacts(patientInput.emergencyContacts, patient)
        updateDoctors(patientInput.doctors, patient)
        updateThumbnail(patientInput.thumbnail, patient)

        return patientEntityToPatientConverter.convert(patient)
    }

    private suspend fun updateNameInfo(nameInfo: Updatable<UpdateNameInfo>, patient: PatientEntity) {
        when (nameInfo) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::nameInfoEntity) }
            }
            is Updatable.Update -> {
                updateNameInfoService.updateNameInfo(nameInfo.value, patient.nameInfoEntity)
            }
        }
    }

    private suspend fun updateEmails(emails: Updatable<List<CreateEmail>>, patient: PatientEntity) {
        when (emails) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::emails) }
            }

            is Updatable.Update -> {
                val newEmails = emails.value.map { createEmailService.createEmail(it) }
                patient.emails.clear()
                logger.trace { ClearedEntityCollectionMessage(patient, patient::emails) }
                patient.emails.addAll(newEmails)
                logger.trace { AddedAllEntityCollectionMessage(patient, patient::emails, newEmails) }
            }
        }
    }

    private suspend fun updateAddresses(addresses: Updatable<List<CreateAddress>>, patient: PatientEntity) {
        when (addresses) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::addresses) }
            }

            is Updatable.Update -> {
                val newAddresses = addresses.value.map { createAddressService.createAddress(it) }
                patient.addresses.clear()
                logger.trace { ClearedEntityCollectionMessage(patient, patient::addresses) }
                patient.addresses.addAll(newAddresses)
                logger.trace { AddedAllEntityCollectionMessage(patient, patient::addresses, newAddresses) }
            }
        }
    }


    private suspend fun updateEmergencyContacts(
        emergencyContacts: Updatable<List<CreateContact>>,
        patient: PatientEntity,
    ): Result4k<Unit, PersistenceError> {
        when (emergencyContacts) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::contacts) }
            }

            is Updatable.Update -> {
                val newEmergencyContacts = emergencyContacts.value.map {
                    createContactService.createContact(
                        it, (patientEntityToPatientConverter.convert(patient))
                    )
                }
                logger.trace { ClearedEntityCollectionMessage(patient, patient::contacts) }
                patient.contacts.addAll(newEmergencyContacts)
                logger.trace { AddedAllEntityCollectionMessage(patient, patient::contacts, newEmergencyContacts) }
            }
        }
        return Success(Unit)
    }

    private suspend fun updatePhones(phones: Updatable<List<CreatePhone>>, patient: PatientEntity) {
        when (phones) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::phones) }
            }

            is Updatable.Update -> {
                val newPhones = phones.value.map { createPhoneService.createPhone(it) }
                patient.phones.clear()
                logger.trace { ClearedEntityCollectionMessage(patient, patient::phones) }
                patient.phones.addAll(newPhones)
                logger.trace { AddedAllEntityCollectionMessage(patient, patient::phones, newPhones) }
            }
        }
    }


    private suspend fun updateThumbnail(thumbnail: Deletable<String>, patient: PatientEntity) {
        when (thumbnail) {
            is Deletable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::thumbnail) }
            }

            is Deletable.Update -> {
                val newThumbnail = Base64.getDecoder().decode(thumbnail.value)
                patient.thumbnail = newThumbnail
            }

            is Deletable.Delete -> {
                logger.trace { SetToNullEntityFieldMessage(patient, patient::thumbnail) }
                patient.thumbnail = null
            }
        }
    }


    private suspend fun updateDoctors(doctors: Updatable<List<DoctorDto.DoctorDtoId>>, patient: PatientEntity) {
        when (doctors) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(patient, patient::doctors) }
            }

            is Updatable.Update -> {

                patient.clearDoctors()
                doctors.value
                    .map { it to doctorRepository.findByIdOrNull(it.value) }
                    .forEach { (id, doctor) ->
                        doctor
                            ?.let { patient.addDoctor(doctor) }
                            ?: logger.warn { MissedCollectionAddNoEntityWithId(patient, patient::doctors, id.value) }
                    }
            }
        }

    }
}
