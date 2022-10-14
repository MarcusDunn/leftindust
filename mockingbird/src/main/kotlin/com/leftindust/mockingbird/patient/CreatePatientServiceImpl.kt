package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.MockingbirdException
import com.leftindust.mockingbird.PersistenceError
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.contact.CreateContactPatient
import com.leftindust.mockingbird.contact.CreateContactService
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneService
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.onFailure
import javax.transaction.Transactional
import mu.KotlinLogging
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class CreatePatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val createNameInfoService: CreateNameInfoService,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val createPhoneService: CreatePhoneService,
    private val createContactService: CreateContactService,
    private val doctorRepository: DoctorRepository,
    private val patientEntityToPatientConverter: PatientEntityToPatientConverter
) : CreatePatientService {
    private val logger = KotlinLogging.logger { }

    override suspend fun addNewPatient(patient: CreatePatient): Result4k<Patient, PersistenceError> {
        val newPatient = PatientEntity(
            nameInfoEntity = createNameInfoService.createNameInfo(patient.nameInfo),
            addresses = patient.addresses.map { createAddressService.createAddress(it) }.toMutableSet(),
            emails = patient.emails.map { createEmailService.createEmail(it) }.toMutableSet(),
            phones = patient.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            events = mutableSetOf(),
            user = null,
            thumbnail = patient.thumbnail?.let { Base64.decode(patient.thumbnail) },
            sex = patient.sex,
            dateOfBirth = patient.dateOfBirth,
            gender = patient.gender,
            ethnicity = patient.ethnicity,
            insuranceNumber = patient.insuranceNumber,
            contacts = mutableSetOf(),
            doctors = mutableSetOf(),
            assignedSurveys = mutableSetOf(),
        )
        patient.doctors
            .map { it to doctorRepository.findByIdOrNull(it.value) }
            .forEach {
                it.second?.addPatient(newPatient)
                    ?: logger.debug { "did not add a doctor in addNewPatient with ${it.first}" }
            }

        val savedPatient = patientRepository.save(newPatient)

        savedPatient.id?.let { uuid ->
            patient.contacts
                .map {
                    CreateContactPatientImpl(
                        patientId = savedPatient.id
                            ?: return PersistenceError.CreateException.invoke(PatientEntity::class, "null entity id returned on persist"),
                        nameInfo = it.nameInfo,
                        relationship = it.relationship,
                        phones = it.phones,
                        emails = it.emails
                    )
                }
                .map { createContactService.createContact(it).onFailure { error ->  return error } }
                .forEach { newPatient.addContact(it) }
        }

        return Success(patientEntityToPatientConverter.convert(savedPatient))
    }
}

data class CreateContactPatientImpl(
    override val patientId: UUID,
    override val nameInfo: CreateNameInfo,
    override val relationship: Relationship,
    override val phones: List<CreatePhone>,
    override val emails: List<CreateEmail>
) : CreateContactPatient