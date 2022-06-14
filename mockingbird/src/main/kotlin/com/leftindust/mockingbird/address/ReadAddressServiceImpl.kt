package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadAddressServiceImpl(
    private val readDoctorService: ReadDoctorService,
    private val readPatientService: ReadPatientService,
) : ReadAddressService {
    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Set<Address>? {
        val doctor = readDoctorService.getByDoctorId(doctorId)
            ?: return null
        return doctor.addresses
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Set<Address>? {
        val byPatientId = readPatientService.getByPatientId(patientId)
            ?: return null
        return byPatientId.addresses
    }
}