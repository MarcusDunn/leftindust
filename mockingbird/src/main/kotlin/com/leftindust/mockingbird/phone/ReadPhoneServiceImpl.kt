package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadPhoneServiceImpl(
    private val readContactService: ReadContactService,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository
) : ReadPhoneService {
    override suspend fun getByPhoneId(phoneId: PhoneDto.PhoneDtoId): Phone? {
        TODO("Not yet implemented")
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Phone>? {
        val doctor = doctorRepository.findByIdOrNull(doctorId.value) ?: return null
        return doctor.phones.toList()
    }

    override suspend fun getByContactId(contactId: ContactDto.ContactDtoId): List<Phone>? {
        val contact = readContactService.getByContactId(contactId)
            ?: return null
        return contact.phone.toList()
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<Phone>? {
        val patient = patientRepository.findByIdOrNull(patientId.value) ?: return null
        return patient.phones.toList()
    }
}