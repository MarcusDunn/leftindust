package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadPhoneServiceImpl(
    private val readDoctorService : ReadDoctorService,
    private val readContactService : ReadContactService,
    private val readPatientService : ReadPatientService
) : ReadPhoneService {
    override suspend fun getByPhoneId(phoneId: PhoneDto.PhoneDtoId): Phone? {
        TODO("Not yet implemented")
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Phone>? {
        val doctor = readDoctorService.getByDoctorId(doctorId)
            ?: return null
        return doctor.phones.toList()
    }

    override suspend fun getByContactId(contactId: ContactDto.ContactDtoId): List<Phone>? {
        val contact = readContactService.getByContactId(contactId)
            ?: return null
        return contact.phone.toList()
    }

    override suspend fun getPatientId(patientId: PatientDto.PatientDtoId): List<Phone>? {
        val patient = readPatientService.getByPatientId(patientId)
            ?: return null
        return patient.phones.toList()
    }
}