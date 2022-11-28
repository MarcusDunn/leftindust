package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.doctor.DoctorRepository
import org.springframework.data.repository.findByIdOrNull
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
@Transactional
class CreateClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val doctorRepository: DoctorRepository,
    private val createAddressService: CreateAddressService,
    private val clinicEntityToClinicConverter: InfallibleConverter<ClinicEntity,Clinic>,
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
        return clinicEntityToClinicConverter.convert(newClinic)
    }
}