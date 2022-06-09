package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.contact.ContactDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.patient.PatientDto
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadPhoneServiceImpl : ReadPhoneService {
    override fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun getByContactId(contactContactDtoId: ContactDto.ContactDtoId): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun getPatientId(patientId: PatientDto.PatientDtoId): List<Phone> {
        TODO("Not yet implemented")
    }
}