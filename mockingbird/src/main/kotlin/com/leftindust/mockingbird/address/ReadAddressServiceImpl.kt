package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.ReadPatientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadAddressServiceImpl(
    private val readDoctorService: ReadDoctorService,
    private val readPatientService: ReadPatientService,
) : ReadAddressService {
    private val logger = LoggerFactory.getLogger(ReadAddressServiceImpl::class.java)
    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): Flow<Address>? {
        val doctor = readDoctorService.getByDoctorId(doctorId)
            ?: return null
        return doctor.addresses.asFlow()
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): Flow<Address>? {
        val byPatientId = readPatientService.getByPatientId(patientId)
            ?: return null
        return byPatientId.addresses.asFlow()
    }
}