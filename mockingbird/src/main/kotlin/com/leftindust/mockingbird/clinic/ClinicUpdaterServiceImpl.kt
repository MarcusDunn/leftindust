package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.graphql.types.Updatable
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class ClinicUpdaterServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val createAddressService: CreateAddressService,
    private val readDoctorService: ReadDoctorService,
) : UpdateClinicService {
    private val logger = LoggerFactory.getLogger(ClinicUpdaterServiceImpl::class.java)

    override suspend fun editClinic(clinicEdit: ClinicEdit): Clinic? {
        val clinicId = clinicEdit.cid.value
        val clinic = clinicRepository.findById(clinicId).orElse(null)
        return if (clinic == null) {
            logger.warn(LogMessage("editClinic call with $clinicEdit did not update any clinic", "No clinic with $clinicId was found").toString())
            null
        } else {
            updateAddress(clinicEdit.address, clinic)
            updateDoctors(clinicEdit.doctors, clinic)
            updateName(clinicEdit.name, clinic)
            clinicRepository.save(clinic)
        }
    }

    private fun updateName(
        nameEdit: Updatable<String>,
        clinic: Clinic,
    ) {
        when (nameEdit) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $clinic name", "Update was $nameEdit").toString())
            }
            is Updatable.Update -> {
                clinic.name = nameEdit.value
                logger.trace(LogMessage("Updated $clinic name to ${nameEdit.value}", "Update was $nameEdit").toString())
            }
        }
    }

    private suspend fun updateDoctors(
        doctorsEdit: Updatable<List<DoctorDto.DoctorDtoId>>,
        clinic: Clinic,
    ) {
        when (doctorsEdit) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $clinic doctors", "Update was $doctorsEdit").toString())
            }
            is Updatable.Update -> {
                val doctorIds = doctorsEdit.value
                val newDoctors = doctorIds.map { readDoctorService.getByDoctorId(it) }

                // we expect all doctors to be valid to update to - if they are not we simply do not update that field
                // at all we do however continue to update the rest.
                val notFoundDoctorsId = newDoctors.withIndex().filter { it.value == null }.map { it.index }.map { doctorIds[it] }
                if (notFoundDoctorsId.isNotEmpty()) {
                    logger.warn(LogMessage("Did not update $clinic doctors", "No doctors with id $notFoundDoctorsId").toString())
                } else {
                    val doctors = newDoctors.map { it!! } // this is a safe assertion as we check when we ensure all the ids exist.
                    logger.trace(LogMessage("Updated $clinic doctors to $doctors", "Update was $doctorsEdit and all the ids had corresponding doctors").toString())
                    clinic.clearDoctors()
                    doctors.forEach { clinic.addDoctor(it) }
                }
            }
        }
    }

    private suspend fun updateAddress(
        addressEdit: Updatable<CreateAddressDto>,
        clinic: Clinic,
    ) {
        when (addressEdit) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $clinic address", "Update was $addressEdit").toString())
            }
            is Updatable.Update -> {
                val createAddress = createAddressService.createAddress(addressEdit.value)
                logger.trace(LogMessage("Updated $clinic address to $createAddress", "Update was $addressEdit ").toString())
                clinic.address = createAddress
            }
        }
    }
}