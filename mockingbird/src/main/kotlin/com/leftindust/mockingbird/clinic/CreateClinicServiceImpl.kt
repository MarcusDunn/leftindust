package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.doctor.DoctorRepository
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
@Transactional
class CreateClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val doctorRepository: DoctorRepository,
    private val createAddressService: CreateAddressService,
) : CreateClinicService {
    override suspend fun addClinic(createClinic: CreateClinic): Clinic {
        val address = createAddressService.createAddress(createClinic.address)
        val clinic = ClinicEntity(
            name = createClinic.name,
            address = address,
            doctors = mutableSetOf()
        )

        createClinic
            .doctors
            .map {
                doctorRepository.findByIdOrNull(it.value)
                    ?: throw IllegalArgumentException("Did not add a doctor with id $it to $clinic because no such doctor exists")
            }
            .forEach { clinic.addDoctor(it) }
        val newClinic = clinicRepository.save(clinic)
        return newClinic.toClinic().onFailure { throw it.reason.toMockingbirdException() }
    }
}