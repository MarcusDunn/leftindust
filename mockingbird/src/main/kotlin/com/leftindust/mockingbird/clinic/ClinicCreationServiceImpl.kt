package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.doctor.ReadDoctorService
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class ClinicCreationServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val readDoctorService: ReadDoctorService,
    private val createAddressService: CreateAddressService,
) : ClinicCreationService {
    private val logger = LoggerFactory.getLogger(ClinicCreationServiceImpl::class.java)

    override suspend fun addClinic(createClinic: CreateClinic): ClinicCreationService.ClinicCreationResult {
        val address = createAddressService.createAddress(createClinic.address)
        val doctors = createClinic.doctors.map { readDoctorService.getByDoctorId(it) }
        val missingDoctorIds = doctors.withIndex().filter { it.value == null }.map { createClinic.doctors[it.index] }
        return if (missingDoctorIds.isNotEmpty()) {
            logger.warn(LogMessage("did not create a new clinic", "there were missing doctors $missingDoctorIds").toString())
            ClinicCreationService.ClinicCreationResult.Failure.DoctorIdsDoNotExist(missingDoctorIds)
        } else {
            val clinic = Clinic(createClinic.name, address)
            doctors.map { it!! }.forEach { clinic.addDoctor(it) }
            val save = clinicRepository.save(clinic)
            ClinicCreationService.ClinicCreationResult.Success(save.also {
                logger.trace("Saved $it")
            })
        }
    }
}