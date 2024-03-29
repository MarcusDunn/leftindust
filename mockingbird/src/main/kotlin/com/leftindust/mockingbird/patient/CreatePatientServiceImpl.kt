package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.contact.CreateContactService
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

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
) : CreatePatientService {
    private val logger = KotlinLogging.logger { }

    override suspend fun addNewPatient(patient: CreatePatient): Patient {
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
        val convertedPatient = savedPatient.toPatient().onFailure { throw it.reason.toMockingbirdException() }

        patient.contacts
            .map { createContactService.createContact(it, convertedPatient) }
            .forEach { newPatient.addContact(it) }

        return convertedPatient
    }

}