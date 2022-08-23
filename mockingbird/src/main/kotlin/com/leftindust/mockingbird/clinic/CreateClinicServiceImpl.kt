package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.doctor.ReadDoctorService
import javax.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class CreateClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val readDoctorService: ReadDoctorService,
    private val createAddressService: CreateAddressService,
    private val clinicToClinicEntityConverter: ClinicToClinicEntityConverter,
    private val clinicEntityToClinicConverter: ClinicEntityToClinicConverter
) : CreateClinicService {
    override suspend fun addClinic(createClinic: CreateClinic): Clinic {
        val address = createAddressService.createAddress(createClinic.address)
        val clinic = Clinic(
            id = UUID.randomUUID(),
            name = createClinic.name,
            address = address
        )

        createClinic
            .doctors
            .map {
                readDoctorService.getByDoctorId(it)
                    ?: throw IllegalArgumentException("Did not add a doctor with id $it to $clinic because no such doctor exists")
            }
            .forEach { clinic.addDoctor(it) }
        val newClinic = clinicRepository.save(clinicToClinicEntityConverter.convert(clinic))
        return clinicEntityToClinicConverter.convert(newClinic)
    }
}