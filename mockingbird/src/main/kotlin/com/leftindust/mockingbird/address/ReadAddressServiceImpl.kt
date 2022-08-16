package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.ReadPatientService
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadAddressServiceImpl(
    val addressRepository: AddressRepository,
    val readDoctorService: ReadDoctorService,
    val readPatientService: ReadPatientService,
) : ReadAddressService {
    override suspend fun getByAddressId(addressId: AddressDto.AddressDtoId): Address? {
        return addressRepository.findById(addressId.value).orElse(null)
    }

    override suspend fun getByDoctorId(doctorId: DoctorDto.DoctorDtoId): List<Address>? {
        val doctor = readDoctorService.getByDoctorId(doctorId)
            ?: return null
        return doctor.addresses.toList()
    }

    override suspend fun getByPatientId(patientId: PatientDto.PatientDtoId): List<Address>? {
        val byPatientId = readPatientService.getByPatientId(patientId)
            ?: return null
        return byPatientId.addresses.toList()
    }
}