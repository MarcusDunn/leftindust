package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.doctor.ReadDoctorService
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val readDoctorService: ReadDoctorService,
    private val createAddressService: CreateAddressService,
) : CreateClinicService {
    override suspend fun addClinic(createClinic: CreateClinic): Clinic {
        val address = createAddressService.createAddress(createClinic.address)
        val clinic = Clinic(createClinic.name, address)

        createClinic
            .doctors
            .map {
                readDoctorService.getByDoctorId(it)
                    ?: throw IllegalArgumentException("Did not add a doctor with id $it to $clinic because no such doctor exists")
            }
            .forEach { clinic.addDoctor(it) }

        return clinicRepository.save(clinic)
    }
}